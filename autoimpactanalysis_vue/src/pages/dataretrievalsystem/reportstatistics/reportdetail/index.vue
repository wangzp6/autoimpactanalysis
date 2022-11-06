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
        </div>
        <el-table :data="tableData" border stripe class="tableClass"
                  :header-cell-class-name="headerBg"
                  :header-cell-style="{'text-align':'center'}"
                  :row-style="{height: '0'}"
                  :cell-style="{padding: '0'}"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column prop="reportCode" label="报表编号" width="150" sortable>
            </el-table-column>
            <el-table-column prop="reportName" label="报表名称" sortable>
            </el-table-column>
            <el-table-column prop="projectCode" label="项目编号" width="150" sortable>
            </el-table-column>
            <el-table-column prop="projectName" label="项目名称" width="200" sortable>
            </el-table-column>
            <el-table-column prop="reportState" label="报表状态" width="150" :formatter="reportStateFormat" :filters="reportStateOptions" :filter-method="filterReportState" sortable>
            </el-table-column>
            <el-table-column prop="operationFlag" label="操作标志" width="150" :formatter="operationFlagFormat" :filters="operationFlagOptions" :filter-method="filterOperationFlag" sortable>
            </el-table-column>
            <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                    <el-button type="success" plain class="bottomClass" @click="handleEdit(scope.row)">编辑 <i
                            class="el-icon-edit"></i>
                    </el-button>
                    <el-popconfirm
                            class="margin-l10"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定删除这条数据吗？"
                            @confirm="deleteRow(scope.row.reportId)">
                        <el-button type="danger" plain class="bottomClass" slot="reference">删除 <i
                                class="el-icon-delete"></i></el-button>
                    </el-popconfirm>
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
        <el-dialog title="报表新增" :visible.sync="reportDialogAdd" width="70%" top="10px" style="margin-top: 0px"
                   :show-close="false">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="reportForm">
                <el-form-item label="项目名称" prop="projectId">
                    <el-select v-model="form.projectId" placeholder="请选择项目名称" filterable default-first-option
                               style="width: 600px;">
                        <el-option
                                v-for="item in projectOptions"
                                :key="item.projectId"
                                :label="item.projectCode+'-'+item.projectName"
                                :value="item.projectId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="报表编号" prop="reportCode" class="margin-l10">
                    <el-input v-model="form.reportCode" autocomplete="off" maxlength="100"
                              style="width: 600px;"></el-input>
                </el-form-item>
                <el-form-item label="报表名称" prop="reportName">
                    <el-input v-model="form.reportName" autocomplete="off" maxlength="100"
                              style="width: 600px;"></el-input>
                </el-form-item>
                <el-form-item label="操作标志" prop="operationFlag">
                    <el-select v-model="form.operationFlag">
                        <el-option
                                v-for="item in operationFlagOptions"
                                :key="item.operationFlag"
                                :label="item.label"
                                :value="item.operationFlag">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="报表状态" prop="reportState">
                  <el-select v-model="form.reportState" >
                    <el-option
                        v-for="item in reportStateOptions"
                        :key="item.reportState"
                        :label="item.label"
                        :value="item.reportState">
                    </el-option>
                  </el-select>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="报表编辑" :visible.sync="reportDialogEdit" width="70%" top="10px" style="margin-top: 0px"
                   :show-close="false">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="reportForm">
                <el-form-item label="项目名称" prop="projectId">
                    <el-select v-model="form.projectId" placeholder="请选择项目名称" filterable default-first-option
                               style="width: 600px;">
                        <el-option
                                v-for="item in projectOptions"
                                :key="item.projectId"
                                :label="item.projectCode+'-'+item.projectName"
                                :value="item.projectId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="报表编号" prop="reportCode" class="margin-l10">
                    <el-input v-model="form.reportCode" autocomplete="off" maxlength="100"
                              style="width: 600px;"></el-input>
                </el-form-item>
                <el-form-item label="报表名称" prop="reportName">
                    <el-input v-model="form.reportName" autocomplete="off" maxlength="100"
                              style="width: 600px;"></el-input>
                </el-form-item>
                <el-form-item label="操作标志" prop="operationFlag">
                    <el-select v-model="form.operationFlag" >
                        <el-option
                                v-for="item in operationFlagOptions"
                                :key="item.operationFlag"
                                :label="item.label"
                                :value="item.operationFlag">
                        </el-option>
                    </el-select>
                </el-form-item>
              <el-form-item label="报表状态" prop="reportState">
                <el-select v-model="form.reportState">
                  <el-option
                      v-for="item in reportStateOptions"
                      :key="item.reportState"
                      :label="item.label"
                      :value="item.reportState">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">取 消</el-button>
                <el-button type="primary" @click="edit">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import axios from "../../../../utils/http";

    export default {
        name: "ReportDetail",
        mounted() {

        },
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 10,
                reportId: '',
                reportCode: '',
                reportName: '',
                reportState: '',
                operationFlag: '',
                projectCode: '',
                projectName: '',
                createTime: '',
                rules: {
                    projectId: [
                        {required: true, message: '请选择项目名称', trigger: 'blur'}
                    ],
                    // reportCode: [
                    //     {required: true, message: '请输入报表编号', trigger: 'blur'}
                    // ],
                    reportName: [
                        {required: true, message: '请输入报表名称', trigger: 'blur'}
                    ],
                    operationFlag: [
                        {required: true, message: '请输入操作标志', trigger: 'blur'}
                    ],
                    reportState: [
                      {required: true, message: '请输入报表状态', trigger: 'blur'}
                    ]
                },
                projectOptions: [],
                operationFlagOptions: [{
                    operationFlag: 'A',
                    label: '新增',
                    text:'新增',
                    value:'A'
                }, {
                    operationFlag: 'U',
                    label: '修改',
                    text:'修改',
                    value:'U'
                }],
                reportStateOptions: [{
                  reportState: '0',
                  label: '未上线',
                  text:'未上线',
                  value:'0'
                }, {
                  reportState: '1',
                  label: '已上线',
                  text:'已上线',
                  value:'1'
                }, {
                  reportState: '2',
                  label: '已下线',
                  text:'已下线',
                  value:'2'
                }],
                form: {
                    projectId: '',
                    reportCode: '',
                    reportName: '',
                    reportState:'',
                    operationFlag: '',
                },
                fileForm: {},
                reportDialogAdd: false,
                reportDialogEdit: false,
                multipleSelection: [],
                headerBg: 'headerBg',
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
            // 加载
            load() {
                axios.get(
                    "/reportDetail/findReports", {
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
                    this.total = data.total;
                });
            },
            reportStateFormat(row){
              if (row.reportState === "2") {
                return '已下线';
              } else if(row.reportState === "1"){
                return '已上线';
              }else{
                return '未上线';
              }
            },
            filterReportState(value, row){
              return row.reportState === value;
            },
            operationFlagFormat(row){
              return row.operationFlag == 'A' ? '新增' : '修改';
            },
            filterOperationFlag(value, row){
              return row.operationFlag === value;
            },
            //重置
            reset() {
                this.reportCode = "";
                this.reportName = "";
                this.projectId = "";
                this.projectCode = "";
                this.projectName = "";
                this.createTime = "";
                this.pageNum = 1;
                this.projectOptions = [];
                this.load()
            },
            //新增
            handleAdd() {
                this.form = {};
                this.projectOptions = [];
                this.projectId = "";
                this.projectCode = '';
                this.projectName = '';
                axios.get("/projectDetail/findAll").then(data => {
                    this.projectOptions = data;
                });
                this.reportDialogAdd = true;
            },
            //保存
            save() {
                this.$refs['reportForm'].validate((valid) => {
                    if (valid) {
                        axios.post("/reportDetail/save", this.form).then(data => {
                            if (data) {
                                this.$message.success("保存成功");
                            } else {
                                this.$message.error("保存失败");
                            }
                            this.load()
                        });
                        this.reportDialogAdd = false;
                    } else {
                        alert('信息填写不完整!');
                    }
                })
            },
            //编辑
            handleEdit(row) {
              this.form = row;
              axios.get("/projectDetail/findAll").then(data => {
                this.projectOptions = data;
              });
              this.reportDialogEdit = true;
            },
            //修改
            edit() {
              this.$refs['reportForm'].validate((valid) =>{
                if (valid) {
                  axios.post("/reportDetail/edit", this.form).then(data => {
                    console.log(data);
                    if (data) {
                      this.$message.success("修改成功");
                    } else {
                      this.$message.error("修改失败");
                    }
                    this.load()
                  });
                  this.reportDialogEdit = false;
                } else {
                    alert('信息填写不完整!');
                }
              });
            },
            //退出
            quit() {
                this.form = {};
                this.projectOptions = [];
                this.projectId = '';
                this.projectCode = '';
                this.projectName = '';
                this.reportDialogAdd = false;
                this.reportDialogEdit = false;
                this.load()
            },
            //删除
            deleteRow(id) {
                axios.delete("/reportDetail/delete/" + id).then(data => {
                    if (data) {
                        this.$message.success("删除成功");
                    } else {
                        this.$message.error("删除失败");
                    }
                    this.load()
                });
            },
            // 多选
            handleSelectionChange(val) {
                this.multipleSelection = val; //[{1,username,...},{2,username,...}]
                // for (let i = 0; i < val.length; i++) {
                //     let item = {};
                //     item["reportCode"] = val[i].reportCode;
                //     item["reportName"] = val[i].reportName;
                //     item["reportDetail"] = val[i].reportDetail;
                //     this.downloadSelection[i] = item;
                // }
            },
            // 批量删除
            deleteBatch() {
                let reportIds = this.multipleSelection.map(v => v.reportId); //[{1,username,...},{2,username,...}]=>[1,2,3]
                axios.post("/reportDetail/deleteBatch/", reportIds).then(data => {
                    if (data) {
                        this.$message.success("批量删除成功");
                    } else {
                        this.$message.error("批量删除失败");
                    }
                    this.multipleSelection = [];
                    this.load()
                });
            }
        }
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
</style>
