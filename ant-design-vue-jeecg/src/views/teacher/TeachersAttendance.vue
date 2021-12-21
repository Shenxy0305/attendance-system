<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <!-- 搜索区域 -->
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="5" :sm="4">
            <a-form-item label="学生姓名" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <a-input placeholder="" v-model="queryParam.name" placeholder="请输入学生姓名"></a-input>
            </a-form-item>
          </a-col>
          <!-- 学生性别 -->
          <!-- <a-col :md="4" :sm="3">
            <a-form-model-item label="性别" prop="sex">
              <j-dict-select-tag v-model="queryParam.sex" title="性别" dictCode="sex" placeholder="请选择性别"/>
            </a-form-model-item>
          </a-col> -->
          <a-col :md="5" :sm="4">
            <a-form-model-item label="学生班级" prop="clazz" >
              <clazz-select v-model="queryParam.clazz" placeholder="请选择学生班级" :options="clazzData"/>
            </a-form-model-item>
          </a-col>
          <a-col :md="7" :sm="5">
            <a-form-model-item label="当前考勤状态" prop="status" >
              <clazz-select v-model="queryParam.status" placeholder="请选择学生当前考勤状态" :options="studentStatus"/>
            </a-form-model-item>
          </a-col>
          <!--
          <a-col :md="11" :sm="12">
            <a-form-item label="创建时间" :labelCol="{span: 5}" :wrapperCol="{span: 18, offset: 1}">
              <j-date v-model="queryParam.createTime_begin" :showTime="true" date-format="YYYY-MM-DD HH:mm:ss" style="width:45%" placeholder="请选择开始时间" ></j-date>
              <span style="width: 10px;">~</span>
              <j-date v-model="queryParam.createTime_end" :showTime="true" date-format="YYYY-MM-DD HH:mm:ss" style="width:45%" placeholder="请选择结束时间"></j-date>
            </a-form-item>
          </a-col>
          -->
          <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
        <a-col :md="6" :sm="12">
          <a-button type="primary" @click="searchQuery" icon="search" style="margin-left: 1px">查询</a-button>
          <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
        </a-col>
      </span>
        </a-row>
      </a-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- TODO:考勤js -->
      <a-button @click="" type="primary" >签到</a-button>
      <a-button @click="" type="primary" >迟到</a-button>
      <a-button @click="" type="primary" >早退</a-button>
      <a-button @click="" type="primary" >旷课</a-button>
      <a-button @click="" type="primary" >撤销</a-button>
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('职务表')">导出</a-button>
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
      </a-dropdown> -->
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
        rowKey="id"
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
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
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
    <sysPosition-modal ref="modalForm" @ok="modalFormOk"></sysPosition-modal>
  </a-card>
</template>

<script>
  import SysPositionModal from './modules/SysPositionModal'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import JDictSelectTag from '../../components/dict/JDictSelectTag.vue'
  import ClazzSelect from './modules/ClazzSelect'

  export default {
    name: 'TeachersAttendance',
    mixins: [JeecgListMixin],
    components: {
      ClazzSelect,
      SysPositionModal,
      JDictSelectTag
    },
    data() {
      return {
        description: '教师授课考勤页面',
        // 假数据
        studentStatus: [
          {text: '全体', value: ''},
          {text: '未签到', value: '0001'},
          {text: '已签到', value: '0002'},
          {text: '迟到', value: '0003'},
          {text: '早退', value: '0004'},
          {text: '旷课', value: '0005'}
        ],
        // 假数据
        clazzData: [
          {text: '全体', value: ''},
          {text: '软件一班', value: '0001'},
          {text: '软件二班', value: '0002'},
          {text: '软件三班', value: '0003'},
          {text: '软件四班', value: '0004'}
        ],
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
              title: '学生姓名',
              align: 'left',
              dataIndex: 'name',
              width: 80
            },
            {
              title: '状态',
              align: 'left',
              dataIndex: 'status'
            },
            {
              title: '班级',
              align: 'center',
              dataIndex: 'clazz'
            },
        ],
        url: {
          list: '/sys/position/list',
          delete: '/sys/position/delete',
          deleteBatch: '/sys/position/deleteBatch',
          exportXlsUrl: '/sys/position/exportXls',
          importExcelUrl: 'sys/position/importExcel',
        },
      }
    },
    computed: {
      importExcelUrl: function () {
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`
      }
    },
    methods: {
      loadData() {
        // TODO:当前课程学生查询
        // 假数据
        this.dataSource = [
          {
            name:"杨航",
            status:"早退",
            clazz:"软件三班"
          },
          {
            name:"韩雪娇",
            status:"已签到",
            clazz:"软件三班"
          },
          {
            name:"沈欣垣",
            status:"旷课",
            clazz:"软件一班"
          },
          {
            name:"高铭远",
            status:"迟到",
            clazz:"软件一班"
          },
          {
            name:"武瑶",
            status:"未记录",
            clazz:"软件一班"
          }
        ]
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>