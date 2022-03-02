<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <!-- 搜索区域 -->
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <!-- 教师工号 -->
          <a-col :md="5" :sm="4">
            <a-form-item label="教师工号" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <a-input placeholder="" v-model="queryParam.account" placeholder="请输入教师工号"></a-input>
            </a-form-item>
          </a-col>
          <!-- 教师姓名 -->
          <a-col :md="5" :sm="4">
            <a-form-item label="教师姓名" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <a-input placeholder="" v-model="queryParam.name" placeholder="请输入教师姓名"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <!-- 教师性别 -->
            <a-col :md="4" :sm="3">
              <a-form-model-item label="性别" prop="sex">
                <j-dict-select-tag v-model="queryParam.sex" title="性别" dictCode="sex" placeholder="请选择性别"/>
              </a-form-model-item>
            </a-col>
            <!-- 教师专业 -->
            <a-col :md="5" :sm="4">
              <a-form-model-item label="教师专业" prop="major" >
                <major-select v-model="queryParam.majorId" placeholder="请输入教师专业" :options="majorData" dict="at_major,major_name,major_id" :pageSize="6" :async="true"/>
              </a-form-model-item>
            </a-col>
            <!-- 教师教学班级 -->
            <a-col :md="5" :sm="4">
              <a-form-model-item label="教学班级" prop="clazz" >
                <clazz-select v-model="queryParam.clazzId" placeholder="请输入教学班级" :options="clazzData" dict="at_clazz,clazz_name,clazz_id" :pageSize="6" :async="true"/>
              </a-form-model-item>
            </a-col>
            <!-- 教师教学课程 -->
            <a-col :md="5" :sm="4">
              <a-form-model-item label="教学课程" prop="course" >
                <clazz-select v-model="queryParam.CourseId" placeholder="请输入教学课程" :options="clazzData" dict="at_course,course_name,course_id" :pageSize="6" :async="true"/>
              </a-form-model-item>
            </a-col>
            <!-- 教师手机号 -->
            <a-col :md="5" :sm="4">
              <a-form-item label="手机号" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
                <a-input placeholder="" v-model="queryParam.phone" placeholder="请输入教师手机号"></a-input>
              </a-form-item>
            </a-col>
          </template>
        <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
      </span>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('学生表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel">
            <a-icon type="delete"/>
            删除
          </a-menu-item>
        </a-menu>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="account"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange">

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical"/>
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down"/></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.userId)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>
    <!-- table区域-end -->

    <!-- 表单区域 -->
    <student-modal ref="modalForm" @ok="modalFormOk"></student-modal>
  </a-card>
</template>

<script>
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDictSelectTag from '../../components/dict/JDictSelectTag.vue'
  import ClazzSelect from './modules/ClazzSelect'
  import MajorSelect from './modules/MajorSelect'
  import StudentModal from './modules/TeacherModal'

  export default {
    name: 'StudentManagement',
    mixins: [JeecgListMixin],
    components: {
      ClazzSelect,
      MajorSelect,
      JDictSelectTag,
      StudentModal
    },
    data() {
      return {
        description: '教师管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key: 'rowIndex',
            width: 60,
            align: 'center',
            customRender: function (t, r, index) {
              return parseInt(index) + 1
            }
          },
          {
            title: '工号',
            align: 'center',
            dataIndex: 'account'
          },
          {
            title: '教师姓名',
            align: 'center',
            dataIndex: 'name'
          },
          {
            title: '教学专业',
            align: 'center',
            dataIndex: 'majorName'
          },
          {
            title: '教学班级',
            align: 'center',
            dataIndex: 'clazzName'
          },
          {
            title: '教学课程',
            align: 'center',
            dataIndex: 'courseName'
          },
          {
            title: '手机号',
            align: 'center',
            dataIndex: 'phone'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            width:180,
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: '/teacher/list',
          delete: '/teacher/delete',
          deleteBatch: '',
          exportXlsUrl: '',
          importExcelUrl: '',
        },
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
      }
    },
    methods: {
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>