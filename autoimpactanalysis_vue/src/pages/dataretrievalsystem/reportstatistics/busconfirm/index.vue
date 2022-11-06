<template>
  <div>
    <div style="margin-bottom: 10px">

    </div>
    <div style="padding-bottom: 10px; padding-left: 20px;">
      <el-input style="width: 200px" placeholder="请输入报表编号" suffix-icon="el-icon-search" class="margin-l5"
                v-model="reportCode"></el-input>
      <el-input style="width: 200px" placeholder="请输入报表名称" suffix-icon="el-icon-search" class="margin-l5"
                v-model="reportName"></el-input>
      <el-input style="width: 200px" placeholder="请输入项目编号" suffix-icon="el-icon-search" class="margin-l5"
                v-model="projectCode"></el-input>
      <el-input style="width: 200px" placeholder="请输入项目名称" suffix-icon="el-icon-search" class="margin-l5"
                v-model="projectName"></el-input>
      <el-button class="margin-l5" type="primary" @click="load">搜索 <i class="el-icon-search"></i></el-button>
      <el-button class="margin-l5" type="warning" @click="reset">重置 <i class="el-icon-close"></i></el-button>
      <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-plus"></i></el-button>
      <el-popconfirm
          style="margin-left: 10px; margin-right: 10px;"
          confirm-button-text='好的'
          cancel-button-text='不用了'
          icon="el-icon-info"
          icon-color="red"
          title="确定删除这些数据吗？"
          @confirm="deleteBatch">
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-minus"></i></el-button>
      </el-popconfirm>
      <el-button type="primary" @click="exportBatchClick">批量导出 <i class="el-icon-download"></i></el-button>
    </div>
    <el-table :data="tableData" border stripe class="tableClass"
              :header-cell-class-name="headerBg"
              :header-cell-style="{'text-align':'center'}"
              :row-style="{height: '0'}"
              :cell-style="{padding: '0'}"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"></el-table-column>
      <el-table-column prop="reportCode" label="报表编号" width="110" sortable>
      </el-table-column>
      <el-table-column prop="reportName" label="报表名称" width="110" sortable>
      </el-table-column>
      <el-table-column prop="projectCode" label="项目编号" width="110" sortable>
      </el-table-column>
      <el-table-column prop="projectName" label="项目名称" width="110" sortable>
      </el-table-column>
      <el-table-column prop="busConfirmState" label="业务确认状态" width="150" :formatter="busConfirmStateFormat" :filters="busConfirmStateOptions" :filter-method="filterBusConfirmState" sortable>
      </el-table-column>
      <el-table-column prop="busConfirmType" label="业务确认类型" width="150" :formatter="busConfirmTypeFormat" :filters="busConfirmTypeOptions" :filter-method="filterBusConfirmType" sortable>
      </el-table-column>
      <el-table-column prop="busConfirmTime" label="业务确认时间" width="130" sortable>
        <template slot-scope="scope">{{scope.row.busConfirmTime| dateYMDFormat}}</template>
      </el-table-column>
      <el-table-column prop="questioner" label="提问人" width="90" sortable>
      </el-table-column>
      <el-table-column prop="busConfirmQuestion" label="业务确认问题" width="110" class="downloadWord">
        <template slot-scope="scope">
          <div>{{scope.row.busConfirmQuestion}}</div>
          <div>
            <el-button icon="el-icon-more" class="bottomClassDetail"
                       @click="handleShowDetailQuestion(scope.row)"></el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="replier" label="回复人" width="90" sortable>
      </el-table-column>
      <el-table-column prop="busConfirmReply" label="业务确认回答" width="110" class="downloadWord">
        <template slot-scope="scope">
          <div>{{scope.row.busConfirmReply}}</div>
          <div>
            <el-button icon="el-icon-more" class="bottomClassDetail"
                       @click="handleShowDetailReply(scope.row)"></el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="operator" label="操作人" width="110" sortable>
      </el-table-column>
      <el-table-column label="操作" width="310">
        <template slot-scope="scope">
          <el-button type="success" plain class="bottomClass" @click="handleView(scope.row)">查看 <i
              class="el-icon-view"></i>
          </el-button>
          <el-button type="success" plain class="bottomClass" @click="handleEdit(scope.row)">编辑 <i
              class="el-icon-edit"></i>
          </el-button>
          <el-popconfirm
              class="margin-l10r5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="定稿之后不可修改！"
              @confirm="editComfirmState(scope.row)">
            <el-button type="success" plain class="bottomClass" slot="reference"ß>定稿 <i
                class="el-icon-edit"></i></el-button>
          </el-popconfirm>
          <el-popconfirm
              class="margin-l10r5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="确定删除这条数据吗？"
              @confirm="deleteRow(scope.row)">
            <el-button type="danger" plain class="bottomClass" slot="reference">删除 <i
                class="el-icon-delete"></i></el-button>
          </el-popconfirm>
          <el-button type="success" plain class="bottomClass" @click="exportClick(scope.row)"
                     style="margin-left: 5px">报表导出<i class="el-icon-download"></i></el-button>
        </template>
      </el-table-column>
    </el-table>
    <div style="padding: 10px 0">
      <!--current-page：页码-->
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>
    <el-dialog title="业务确认新增" :visible.sync="busConfirmDialogAdd" width="70%" top="10px" style="margin-top: 0px"
               :show-close="false">
      <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="busConfirmForm">
        <el-form-item label="项目名称" prop="projectId">
          <el-select v-model="form.projectId" placeholder="请选择项目名称" filterable default-first-option
                     style="width: 600px;" @change="getReportListAdd(form.projectId)">
            <el-option
                v-for="p in projectOptions"
                :key="p.projectId"
                :label="p.projectCode+'-'+p.projectName"
                :value="p.projectId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报表名称" prop="reportId">
          <el-select v-model="form.reportId" placeholder="请选择报表名称" filterable default-first-option
                     style="width: 600px;">
            <el-option
                v-for="r in reportOptions"
                :key="r.reportId"
                :label="r.reportCode+'-'+r.reportName"
                :value="r.reportId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="业务确认类型" prop="busConfirmType">
          <el-select v-model="form.busConfirmType">
            <el-option
                v-for="item in busConfirmTypeOptions"
                :key="item.busConfirmType"
                :label="item.label"
                :value="item.busConfirmType">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="业务确认时间" prop="busConfirmTime">
          <el-date-picker
              v-model="form.busConfirmTime"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="提问人" prop="questioner">
          <el-input v-model="form.questioner" autocomplete="off" maxlength="50"></el-input>
        </el-form-item>
        <el-form-item label="业务确认问题">
          <br>
          <TinymceEditor v-if="initSuccess" :content.sync="form.busConfirmQuestion"
                         :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
        </el-form-item>
        <el-form-item label="回复人" prop="replier">
          <el-input v-model="form.replier" autocomplete="off" maxlength="50"></el-input>
        </el-form-item>
        <el-form-item label="业务确认回答">
          <br>
          <TinymceEditor v-if="initSuccess" :content.sync="form.busConfirmReply"
                         :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="quit">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="业务确认编辑" :visible.sync="busConfirmDialogEdit" width="70%" top="10px" style="margin-top: 0px"
               :show-close="false">
      <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="busConfirmForm">
        <el-form-item label="项目名称" prop="projectId">
          <el-select v-model="form.projectId" placeholder="请选择项目名称" filterable default-first-option
                     style="width: 600px;" @change="getReportListEdit(form.projectId)">
            <el-option
                v-for="p in projectOptions"
                :key="p.projectId"
                :label="p.projectCode+'-'+p.projectName"
                :value="p.projectId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报表名称" prop="reportId">
          <el-select v-model="form.reportId" placeholder="请选择报表名称" filterable default-first-option
                     style="width: 600px;">
            <el-option
                v-for="r in reportOptions"
                :key="r.reportId"
                :label="r.reportCode+'-'+r.reportName"
                :value="r.reportId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="业务确认类型" prop="busConfirmType">
          <el-select v-model="form.busConfirmType">
            <el-option
                v-for="item in busConfirmTypeOptions"
                :key="item.busConfirmType"
                :label="item.label"
                :value="item.busConfirmType">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="业务确认时间" prop="busConfirmTime">
          <el-date-picker
              v-model="form.busConfirmTime"
              type="date"
              placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="提问人" prop="questioner">
          <el-input v-model="form.questioner" autocomplete="off" maxlength="50"></el-input>
        </el-form-item>
        <el-form-item label="业务确认问题">
          <br>
          <TinymceEditor v-if="initSuccess" :content.sync="form.busConfirmQuestion"
                         :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
        </el-form-item>
        <el-form-item label="回复人" prop="replier">
          <el-input v-model="form.replier" autocomplete="off" maxlength="50"></el-input>
        </el-form-item>
        <el-form-item label="业务确认回答">
          <br>
          <TinymceEditor v-if="initSuccess" :content.sync="form.busConfirmReply"
                         :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="quit">取 消</el-button>
        <el-button type="primary" @click="edit">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="业务确认查看" :visible.sync="busConfirmDialogView" width="70%" top="10px" style="margin-top: 0px"
               :show-close="false">
      <el-form label_witch="100px" size="small">
        <div class="inputCLass">
          项目编号:
          <el-input v-model="form.projectCode" autocomplete="off" :disabled="true" style="width: 200px;"
                    class="borderNone"></el-input>
          项目名称:
          <el-input v-model="form.projectName" autocomplete="off" :disabled="true" style="width: 300px;"
                    class="borderNone"></el-input>
        </div>
        <div class="inputCLass">
          报表编号:
          <el-input v-model="form.reportCode" autocomplete="off" :disabled="true" style="width: 200px;"
                    class="borderNone"></el-input>
          报表名称:
          <el-input v-model="form.reportName" autocomplete="off" :disabled="true" style="width: 300px;"
                    class="borderNone"></el-input>
        </div>
        <div class="inputCLass">
          业务确认状态:
          <el-select v-model="form.busConfirmState" class="noBorderSelect" disabled style="margin-right: 20px">
            <el-option
                v-for="s in busConfirmStateOptions"
                :key="s.busConfirmState"
                :label="s.label"
                :value="s.busConfirmState">
            </el-option>
          </el-select>
          业务确认类型:
          <el-select v-model="form.busConfirmType" class="noBorderSelect" disabled style="margin-right: 20px">
            <el-option
                v-for="t in busConfirmTypeOptions"
                :key="t.busConfirmType"
                :label="t.label"
                :value="t.busConfirmType">
            </el-option>
          </el-select>
        </div>
        提问人:
        <el-input v-model="form.questioner" autocomplete="off" :disabled="true" style="width: 600px;"
                  class="borderNone"></el-input>
        <br>
        业务确认问题内容:
        <TinymceNoEdit v-if="initSuccess" :content.sync="form.busConfirmQuestion"
                       :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
        回复人:
        <el-input v-model="form.replier" autocomplete="off" :disabled="true" style="width: 600px;"
                  class="borderNone"></el-input>
        <br>
        业务确认回答内容:
        <TinymceNoEdit v-if="initSuccess" :content.sync="form.busConfirmReply"
                       :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
        <div class="inputCLass">
          操作人:
          <el-input v-model="form.operator" autocomplete="off" :disabled="true" style="width: 600px;"
                    class="borderNone"></el-input>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="quit">关 闭</el-button>
      </div>
    </el-dialog>
    <el-dialog title="业务确认问题明细" :visible.sync="questionDialogDetail" width="70%" top="10px" style="margin-top: 0px"
               :show-close="false">
      <TinymceView v-if="initSuccess" :content.sync="form.busConfirmQuestion" :disabled="true"/>
      <div slot="footer" class="dialog-footer">
        <el-button @click="quit">关 闭</el-button>
      </div>
    </el-dialog>
    <el-dialog title="业务确认回复明细" :visible.sync="replyDialogDetail" width="70%" top="10px" style="margin-top: 0px"
               :show-close="false">
      <TinymceView v-if="initSuccess" :content.sync="form.busConfirmReply" :disabled="true"/>
      <div slot="footer" class="dialog-footer">
        <el-button @click="quit">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "../../../../utils/http";
import htmlDocx from 'html-docx-js/dist/html-docx';
import saveAs from 'file-saver';

export default {
  name: "BusConfirm",
  mounted() {

  },
  data() {
    return {
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 10,
      busConfirmId: '',
      busConfirmQuestion: '',
      busConfirmReply: '',
      questioner: '',
      replier: '',
      operator: '',
      busConfirmType: '',
      busConfirmState: '',
      busConfirmTime: '',
      reportId: '',
      reportCode: '',
      reportName: '',
      projectId: '',
      projectCode: '',
      projectName: '',
      rules: {
        projectId: [
          {required: true, message: '请选择项目', trigger: 'blur'}
        ],
        reportId: [
          {required: true, message: '请选择报表', trigger: 'blur'}
        ],
        busConfirmType: [
          {required: true, message: '请选择业务确认类型', trigger: 'blur'}
        ]
      },
      projectOptions: [],
      reportOptions: [],
      busConfirmTypeOptions: [{
        busConfirmType: '0',
        label:'业务确认',
        text:'业务确认',
        value:'0'
      }, {
        busConfirmType: '1',
        label:'系统反查',
        text:'系统反查',
        value:'1'
      }],
      busConfirmStateOptions: [{
        busConfirmState: '0',
        label:'未定稿',
        text:'未定稿',
        value:'0'
      }, {
        busConfirmState: '1',
        label:'已定稿',
        text:'已定稿',
        value:'1'
      }],
      initSuccess: false,
      form: {
        busConfirmType: '',
        busConfirmState: '',
        busConfirmTime: '',
        reportId: '',
        reportCode: '',
        reportName: '',
        projectId: '',
        projectCode: '',
        projectName: '',
      },
      busConfirmDialogAdd: false,
      busConfirmDialogEdit: false,
      busConfirmDialogView: false,
      questionDialogDetail: false,
      replyDialogDetail: false,
      multipleSelection: [],
      headerBg: 'headerBg',
      downloadSelection: [],
    }
  },
  created() {
    this.load()
  },
  methods: {
    handleSizeChange(pageSize) {
      console.log(pageSize);
      this.pageSize = pageSize;
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum);
      this.pageNum = pageNum;
      this.load()
    },
    getProjectList() {
      axios.get("/projectDetail/findAll").then(data => {
        console.log("getProjectList")
        console.log(data);
        this.projectOptions = data;
        console.log(this.projectOptions);
      });
    },
    getReportListAdd(projectId) {
      axios.get("/reportDetail/getByProjectId/" + projectId).then(data => {
        console.log("getReportListAdd:"+projectId)
        console.log(data);
        this.reportOptions = data;
        console.log(this.reportOptions);
      });
    },
    getReportListEdit(projectId) {
      this.form.reportId='';
      this.form.reportCode='';
      this.form.reportName='';
      axios.get("/reportDetail/getByProjectId/" + projectId).then(data => {
        console.log("getReportListEdit:"+projectId)
        console.log(data);
        this.reportOptions = data;
        console.log(this.reportOptions);
      });
    },
    busConfirmStateFormat(row){
      return row.busConfirmState == '1' ? '已定稿' : '未定稿';
    },
    filterBusConfirmState(value, row){
      return row.busConfirmState === value;
    },
    busConfirmTypeFormat(row){
      return row.busConfirmType == '1' ? '系统反查' : '业务确认';
    },
    filterBusConfirmType(value, row){
      return row.busConfirmType === value;
    },
    // 加载
    load() {
      axios.get(
          "/busConfirm/findPage", {
            params: {
              pageNum: this.pageNum,
              pageSize: this.pageSize,
              reportCode: this.reportCode,
              reportName: this.reportName,
              projectCode: this.projectCode,
              projectName: this.projectName
            }
          }
      ).then(data => {
        this.tableData = data.records;
        console.log(this.tableData);
        this.total = data.total;
      });
    },
    //重置
    reset() {
      this.reportId = "";
      this.reportCode = "";
      this.reportName = "";
      this.projectId = "";
      this.projectCode = "";
      this.projectName = "";
      this.pageNum = 1;
      this.projectOptions = [];
      this.reportOptions = [];
      this.initSuccess = false;
      this.load()
    },
    //新增
    handleAdd() {
      this.getProjectList();
      this.reportOptions = [];
      this.form = {};
      this.busConfirmQuestion = '';
      this.busConfirmReply = '';
      this.busConfirmDialogAdd = true;
      this.initSuccess = true;
    },
    //保存
    save() {
      this.$refs['busConfirmForm'].validate((valid) => {
        if (valid) {
          let userVO = JSON.parse(localStorage.getItem("user"));
          this.form.operator=userVO.username;
          axios.post("/busConfirm/save", this.form).then(data => {
            if (data) {
              this.$message.success("保存成功");
            } else {
              this.$message.error("保存失败");
            }
            this.load()
          });
          this.busConfirmDialogAdd = false;
          this.initSuccess = false;
        } else {
          alert('信息填写不完整!');
        }
      })
    },
    //编辑
    handleEdit(row) {
      if("1" != row.busConfirmState){
        this.busConfirmQuestion = '';
        this.busConfirmReply = '';
        this.busConfirmState = row.busConfirmState;
        this.busConfirmType = row.busConfirmType;
        this.getProjectList();
        this.reportOptions=this.getReportListEdit(row.projectId);
        this.form = row;
        this.form.reportCode=row.reportCode;
        this.form.reportName=row.reportName;
        let userVO = JSON.parse(localStorage.getItem("user"));
        this.form.operator = this.form.operator+"|"+userVO.username;
        let busConfirmTimestamp = Date.parse(new Date(row.busConfirmTime));
        this.form.busConfirmTime=busConfirmTimestamp;
        this.initSuccess = true;
        this.busConfirmDialogEdit = true;
      }else{
        alert("已定稿，不能编辑！")
      }
    },
    //修改
    edit() {
      this.$refs['busConfirmForm'].validate((valid) =>{
        if (valid) {
          axios.post("/busConfirm/edit", this.form).then(data => {
            console.log(data);
            if (data) {
              this.$message.success("修改成功");
            } else {
              this.$message.error("修改失败");
            }
            this.load()
          });
          this.initSuccess = false;
          this.busConfirmDialogEdit = false;
        } else {
          alert('信息填写不完整!');
        }
      });
    },
    //查看
    handleView(row) {
      this.form = {};
      this.projectOptions = [];
      this.form = row;
      console.log(row)
      this.form.busConfirmQuestion = row.busConfirmQuestion;
      this.form.busConfirmReply = row.busConfirmReply;
      this.busConfirmDialogView = true;
      this.initSuccess = true;
    },
    quit() {
      this.form = {};
      this.projectOptions = [];
      this.reportId = "";
      this.reportCode = "";
      this.reportName = "";
      this.projectId = "";
      this.projectCode = "";
      this.projectName = "";
      this.busConfirmType = '';
      this.form.busConfirmQuestion = '';
      this.form.busConfirmReply = '';
      this.busConfirmDialogAdd = false;
      this.busConfirmDialogEdit = false;
      this.busConfirmDialogView = false;
      this.questionDialogDetail = false;
      this.replyDialogDetail = false;
      this.initSuccess = false;
      this.load()
    },
    //查看问题详情
    handleShowDetailQuestion(row) {
      this.form = {};
      this.form = row;
      this.questionDialogDetail = true;
      this.initSuccess = true;
    },
    //查看回答详情
    handleShowDetailReply(row) {
      this.form = {};
      this.form = row;
      this.replyDialogDetail = true;
      this.initSuccess = true;
    },
    //定稿
    editComfirmState(row){
      if("1" != row.busConfirmState){
      this.form = row;
      let userVO = JSON.parse(localStorage.getItem("user"));
      this.form.operator = this.form.operator+"|"+userVO.username;
      axios.post("/busConfirm/editConfirmState", this.form).then(data => {
        console.log(data);
        if (data) {
          this.$message.success("定稿成功");
        } else {
          this.$message.error("定稿失败");
        }
        this.load()
      });
      }else{
        alert("已定稿，不能再次定稿！")
      }
    },
    //删除
    deleteRow(row) {
      if("1" != row.busConfirmState){
      axios.delete("/busConfirm/delete/" + row.busConfirmId).then(data => {
        if (data) {
          this.$message.success("删除成功");
        } else {
          this.$message.error("删除失败");
        }
        this.load()
      });
      }else{
        alert("已定稿，不能删除！")
      }
    },
    // 多选
    handleSelectionChange(val) {
      this.multipleSelection = val; //[{1,username,...},{2,username,...}]
      for (let i = 0; i < val.length; i++) {
        let item = {};
          item["reportCode"] = val[i].reportCode;
          item["reportName"] = val[i].reportName;
          item["busConfirmType"] = val[i].busConfirmType;
          item["questioner"] = val[i].questioner;
          item["busConfirmQuestion"] = val[i].busConfirmQuestion;
          item["replier"] = val[i].replier;
          item["busConfirmReply"] = val[i].busConfirmReply;
          this.downloadSelection[i] = item;
      }
    },
    // 批量删除
    deleteBatch() {
      let busConfirmStates = this.multipleSelection.map(v => v.busConfirmState); //[{1,username,...},{2,username,...}]=>[1,2,3]
      let flag = false;
      for (let i = 0; i < busConfirmStates.length; i++) {
        if("1" === busConfirmStates[i]){
          flag = true;
          break;
        }
      }
      if(flag===false){
        let busConfirmIds = this.multipleSelection.map(v => v.busConfirmId); //[{1,username,...},{2,username,...}]=>[1,2,3]
        axios.post("/busConfirm/deleteBatch/", busConfirmIds).then(data => {
          if (data) {
            this.$message.success("批量删除成功");
          } else {
            this.$message.error("批量删除失败");
          }
          this.multipleSelection = [];
          this.load()
        });
      }else{
        this.$message.error("存在已定稿数据，不可删除！");
        this.multipleSelection=[];
        this.load()
      }


    },
    exportClick(row) {
      let questionContent = row.busConfirmQuestion;
      let replyContent = row.busConfirmReply;
      let title = '';
      if("1"===row.busConfirmType){
          title = row.reportCode + row.reportName + "系统反查";
      }else{
          title = row.reportCode + row.reportName + "业务确认";
      }
      let page = '<!DOCTYPE html><html><head><meta charset="UTF-8"></head><body><h1>' + title + '</h1><h2>问题：</h2>' + questionContent + '<h2>回答：</h2>'+replyContent+ '</body></html>';
      let converted = htmlDocx.asBlob(page);
      // 用 FielSaver.js里的保存方法 进行输出
      saveAs(converted, title + '.docx');
    },
    exportBatchClick() {
      if (this.downloadSelection.length < 1) {
        alert("请勾选报表")
      } else {
        let content ='';
        let page = '';
        let converted = '';
        let title ='';
        this.downloadSelection=sortKey(this.downloadSelection,'reportCode');
        for (let item of this.downloadSelection) {
          if("1"===item.busConfirmType){
            title = '<h1>' + item.reportCode + item.reportName + "系统反查"+'</h1>';
          }else{
            title = '<h1>' + item.reportCode + item.reportName + "业务确认"+'</h1>';
          }
          content += title +'<h2>问题：</h2>'+ item.busConfirmQuestion +'<h2>回答：</h2>'+item.busConfirmReply;
        }
        page = '<!DOCTYPE html><html><head><meta charset="UTF-8"></head><body>' + content + '</body></html>';
        converted = htmlDocx.asBlob(page);
        // 用 FielSaver.js里的保存方法 进行输出
        saveAs(converted, '报表.docx');
      }
      this.downloadSelection = [];
      this.load();
    }
  }
};
function sortKey(array,key){
  return array.sort(function(a,b){
    let x = a[key];
    let y = b[key];
    return ((x<y)?-1:(x>y)?1:0)
  })
}
</script>

<style lang="scss">
.headerBg {
  background: #F5F5F5 !important;
}

.tableClass .cell {
  max-height: 125px;
  line-height: 25px !important;
}

.bottomClass {
  padding: 5px !important;
}

.bottomClassDetail {
  padding: 5px !important;
  right: 2px;
  bottom: 2px;
  position: absolute;
  border: none;
}

.el-dialog__body {
  padding: 10px 20px !important;
}

.el-textarea.is-disabled .el-textarea__inner {
  color: #000000 !important;
}

.el-input.is-disabled .el-input__inner {
  border: none;
  box-shadow: none;
  padding-right: 20px;
  background-color: rgba(0, 0, 0, 0);
  color: #000000;
}

.inputCLass {
  padding-bottom: 20px;
}

.noBorderSelect.is-disabled .el-input__inner{
  border: none;
  box-shadow: none;
  background-color: rgba(0, 0, 0, 0);
  color: #000000;
}
</style>
