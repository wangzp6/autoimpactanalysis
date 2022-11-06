<template>
    <div>
        <div style="margin-bottom: 10px">

        </div>
        <div style="padding-bottom: 10px; padding-left: 20px;">
            <el-input style="width: 200px" placeholder="请输入报表编号" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="reportId"></el-input>
            <el-input style="width: 200px" placeholder="请输入报表名称" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="reportName"></el-input>
            <el-input style="width: 200px" placeholder="请输入项目编号" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="projectId"></el-input>
            <el-input style="width: 200px" placeholder="请输入项目名称" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="projectName"></el-input>
          <el-input style="width: 200px" placeholder="请输入报表状态" suffix-icon="el-icon-search" class="margin-l5"
                    v-model="reportState"></el-input>
          <el-input style="width: 200px" placeholder="请输入文档类型" suffix-icon="el-icon-search" class="margin-l5"
                    v-model="docType"></el-input>
          <el-input style="width: 200px" placeholder="请输入作者" suffix-icon="el-icon-search" class="margin-l5"
                    v-model="author"></el-input>
            <el-date-picker class="margin-l5"
                            v-model="createTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择项目创建日期"
                            :picker-options="pickerOptions">
            </el-date-picker>
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
                  height="690"
                  :header-cell-style="{'text-align':'center'}"
                  :row-style="{height: '0'}"
                  :cell-style="{padding: '0'}"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column prop="reportId" label="报表编号" width="110" sortable>
            </el-table-column>
            <el-table-column prop="reportName" label="报表名称" width="260" sortable>
            </el-table-column>
            <el-table-column prop="projectId" label="项目编号" width="200" sortable>
            </el-table-column>
            <el-table-column prop="projectName" label="项目名称" width="260" sortable>
            </el-table-column>
            <el-table-column prop="operationFlag" label="操作标志" width="110" sortable>
            </el-table-column>
          <el-table-column prop="reportState" label="报表状态" width="110" sortable>
          </el-table-column>
          <el-table-column prop="docType" label="文档类型" width="110" sortable>
          </el-table-column>
          <el-table-column prop="author" label="作者" width="110" sortable>
          </el-table-column>
            <el-table-column prop="reportDetail" label="报表明细" width="300" class="downloadWord">
                <template slot-scope="scope">
                    <div>{{scope.row.reportDetail}}</div>
                    <div>
                        <el-button icon="el-icon-more" class="bottomClassDetail"
                                   @click="handleShowDetail(scope.row)"></el-button>
                    </div>
                </template>
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
                            title="确定删除这条数据吗？"
                            @confirm="deleteRow(scope.row.id)">
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
        <el-dialog title="报表新增" :visible.sync="dialogFormVisibleAdd" width="70%" top="10px" style="margin-top: 0px"
                   :show-close="false">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="reportForm">
                <el-form-item label="项目名称" prop="projectId">
                    <el-select v-model="form.projectId" placeholder="请选择项目名称" filterable default-first-option
                               style="width: 600px;">
                        <el-option
                                v-for="item in projectOptions"
                                :key="item.projectId"
                                :label="item.projectId+'-'+item.projectName"
                                :value="item.projectId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="报表编号" prop="reportId">
                    <el-input v-model="form.reportId" autocomplete="off" maxlength="100"
                              style="width: 600px;"></el-input>
                </el-form-item>
                <el-form-item label="报表名称" prop="reportName">
                    <el-input v-model="form.reportName" autocomplete="off" maxlength="100"
                              style="width: 600px;"></el-input>
                </el-form-item>
                <el-form-item label="操作标志" prop="operationFlag">
                    <el-select v-model="form.operationFlag" placeholder="A:新增；U:修改">
                        <el-option
                                v-for="item in operationFlagOptions"
                                :key="item.operationFlag"
                                :label="item.label"
                                :value="item.operationFlag">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="报表状态" prop="reportState">
                  <el-select v-model="form.reportState" placeholder="A:已上线；B:未上线">
                    <el-option
                        v-for="item in reportStateOptions"
                        :key="item.reportState"
                        :label="item.label"
                        :value="item.reportState">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="文档类型" prop="docCode">
                  <el-select v-model="form.docCode">
                    <el-option
                        v-for="item in docCodeOptions"
                        :key="item.docCode"
                        :label="item.docCode+'-'+item.docType"
                        :value="item.docCode">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="作者" prop="author">
                  <el-input v-model="form.author" autocomplete="off" maxlength="100"
                            style="width: 600px;"></el-input>
                </el-form-item>
                <el-form-item label="元素明细">
                    <br>
                    <TinymceEditor v-if="initSuccess" :content.sync="form.reportDetail"
                                   :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="报表编辑" :visible.sync="dialogFormVisibleEdit" width="70%" top="10px" style="margin-top: 0px"
                   :show-close="false">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="reportForm">
                <el-form-item label="项目名称" prop="projectId">
                    <el-select v-model="form.projectId" placeholder="请选择项目名称" filterable default-first-option
                               style="width: 600px;">
                        <el-option
                                v-for="item in projectOptions"
                                :key="item.projectId"
                                :label="item.projectId+'-'+item.projectName"
                                :value="item.projectId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="报表编号" prop="reportId">
                    <el-input v-model="form.reportId" autocomplete="off" maxlength="100"
                              style="width: 600px;"></el-input>
                </el-form-item>
                <el-form-item label="报表名称" prop="reportName">
                    <el-input v-model="form.reportName" autocomplete="off" maxlength="100"
                              style="width: 600px;"></el-input>
                </el-form-item>
                <el-form-item label="操作标志" prop="operationFlag">
                    <el-select v-model="form.operationFlag" placeholder="A:新增；U:修改">
                        <el-option
                                v-for="item in operationFlagOptions"
                                :key="item.operationFlag"
                                :label="item.label"
                                :value="item.operationFlag">
                        </el-option>
                    </el-select>
                </el-form-item>
              <el-form-item label="报表状态" prop="reportState">
                <el-select v-model="form.reportState" placeholder="A:已上线；B:未上线">
                  <el-option
                      v-for="item in reportStateOptions"
                      :key="item.reportState"
                      :label="item.label"
                      :value="item.reportState">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="文档类型" prop="docType">
                <el-select v-model="form.docType">
                  <el-option
                      v-for="item in docTypeOptions"
                      :key="item.docType"
                      :label="item.label"
                      :value="item.docType">
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="作者" prop="author">
                <el-input v-model="form.author" autocomplete="off" maxlength="100"
                          style="width: 600px;"></el-input>
              </el-form-item>
                <el-form-item label="元素明细">
                    <br>
                    <TinymceEditor v-if="initSuccess" :content.sync="form.reportDetail"
                                   :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="报表查看" :visible.sync="dialogFormVisibleView" width="70%" top="10px" style="margin-top: 0px"
                   :show-close="false">
            <el-form label_witch="100px" size="small">
                <div class="inputCLass">
                    项目编号:
                    <el-input v-model="form.projectId" autocomplete="off" :disabled="true" style="width: 500px;"
                              class="borderNone"></el-input>
                    项目名称:
                    <el-input v-model="form.projectName" autocomplete="off" :disabled="true" style="width: 500px;"
                              class="borderNone"></el-input>
                </div>
                <div class="inputCLass">
                    报表编号:
                    <el-input v-model="form.reportId" autocomplete="off" :disabled="true" style="width: 500px;"
                              class="borderNone"></el-input>
                    报表名称:
                    <el-input v-model="form.reportName" autocomplete="off" :disabled="true" style="width: 500px;"
                              class="borderNone"></el-input>
                </div>
                <div class="inputCLass">
                    操作标志:
                    <el-input v-model="form.operationFlag" autocomplete="off" :disabled="true" style="width: 100px;"
                              class="borderNone"></el-input>
                    报表状态:
                    <el-input v-model="form.reportState" autocomplete="off" :disabled="true" style="width: 100px;"
                              class="borderNone"></el-input>
                </div>
              <div class="inputCLass">
                  文档类型:
                  <el-input v-model="form.docType" autocomplete="off" :disabled="true" style="width: 100px;"
                            class="borderNone"></el-input>
                  作者:
                  <el-input v-model="form.author" autocomplete="off" :disabled="true" style="width: 100px;"
                            class="borderNone"></el-input>
                </div>
                元素明细:
                <br>
                <TinymceNoEdit v-if="initSuccess" :content.sync="form.reportDetail"
                               :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">关 闭</el-button>
            </div>
        </el-dialog>
        <el-dialog title="报表明细" :visible.sync="dialogFormVisibleDetail" width="70%" top="10px" style="margin-top: 0px"
                   :show-close="false">
            <TinymceView v-if="initSuccess" :content.sync="form.reportDetail" :disabled="true"/>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">关 闭</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import axios from "../../../../utils/http";
    import baseURL from "../../../../utils/baseURL";
    import htmlDocx from 'html-docx-js/dist/html-docx';
    import saveAs from 'file-saver';
    // import $ from 'jquery';

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
                id: '',
                reportId: '',
                reportName: '',
                reportDetail: '',
                reportState: '',
                docCode: '',
                docType: '',
                author: '',
                operationFlag: '',
                projectId: '',
                projectName: '',
                createTime: '',
                rules: {
                    projectId: [
                        {required: true, message: '请选择项目名称', trigger: 'blur'}
                    ],
                    reportId: [
                        {required: true, message: '请输入报表编号', trigger: 'blur'}
                    ],
                    reportName: [
                        {required: true, message: '请输入报表名称', trigger: 'blur'}
                    ],
                    operationFlag: [
                        {required: true, message: '请输入操作标志', trigger: 'blur'}
                    ],
                    reportState: [
                      {required: true, message: '请输入报表状态', trigger: 'blur'}
                    ],
                    docType: [
                      {required: true, message: '请输入文档类型', trigger: 'blur'}
                    ],
                    author: [
                      {required: true, message: '请输入作者', trigger: 'blur'}
                    ]
                },
                projectOptions: [],
                operationFlagOptions: [{
                    operationFlag: 'A',
                    label: 'A'
                }, {
                    operationFlag: 'U',
                    label: 'U'
                }],
                reportStateOptions: [{
                  reportState: 'A',
                  label: 'A'
                }, {
                  reportState: 'B',
                  label: 'B'
                }],
                docCodeOptions: [],
                initSuccess: false,
                form: {
                    projectId: '',
                    reportId: '',
                    reportName: '',
                    operationFlag: '',
                },
                fileForm: {},
                dialogFormVisibleAdd: false,
                dialogFormVisibleEdit: false,
                dialogFormVisibleView: false,
                dialogFormVisibleDetail: false,
                multipleSelection: [],
                headerBg: 'headerBg',
                downLoadUrl: baseURL + "files/downloadWord/",
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
            // 加载
            load() {
                axios.get(
                    "/reportStatistics/findReports", {
                        params: {
                            pageNum: this.pageNum,
                            pageSize: this.pageSize,
                            reportId: this.reportId,
                            reportName: this.reportName,
                            projectId: this.projectId,
                            projectName: this.projectName,
                            createTime: this.createTime
                        }
                    }
                ).then(data => {
                    this.tableData = data.records;
                    this.total = data.total;
                });
            },
            //重置
            reset() {
                this.reportId = "";
                this.reportName = "";
                this.projectId = "";
                this.projectName = "";
                this.createTime = "";
                this.pageNum = 1;
                this.projectOptions = [];
                this.docCodeOptions = [];
                this.load()
            },
            //新增
            handleAdd() {
                this.form = {};
                this.projectOptions = [];
                this.projectId = '';
                this.projectName = '';
                this.reportDetail = '';
                axios.get("/projectDetail/findAll").then(data => {
                    this.projectOptions = data;
                });
                axios.get("/documentCode/findAll").then(data => {
                  this.docCodeOptions = data;
                });
                this.dialogFormVisibleAdd = true;
                this.initSuccess = true;
            },
            //保存
            save() {
                this.$refs['reportForm'].validate((valid) => {
                    if (valid) {
                        axios.post("/reportStatistics/save", this.form).then(data => {
                            if (data) {
                                this.$message.success("保存成功");
                            } else {
                                this.$message.error("保存失败");
                            }
                            this.load()
                        });
                        this.dialogFormVisibleAdd = false;
                        this.dialogFormVisibleEdit = false;
                        this.initSuccess = false;
                    } else {
                        alert('信息填写不完整!');
                    }
                })
            },
            //查看
            handleView(row) {
                this.form = {};
                this.projectOptions = [];
                this.projectId = '';
                this.projectName = '';
                this.docCodeOptions = [];
                this.docCode = '';
                this.docType = '';
                this.form = row;
                this.form.reportDetail = row.reportDetail;
                this.dialogFormVisibleView = true;
                this.initSuccess = true;
            },
            quit() {
                this.form = {};
                this.projectOptions = [];
                this.projectId = '';
                this.projectName = '';
                this.docCodeOptions = [];
                this.docCode = '';
                this.docType = '';
                this.form.reportDetail = '';
                this.dialogFormVisibleAdd = false;
                this.dialogFormVisibleEdit = false;
                this.dialogFormVisibleView = false;
                this.dialogFormVisibleDetail = false;
                this.initSuccess = false;
                this.load()
            },
            //编辑
            handleEdit(row) {
                this.form.reportDetail = '';
                this.form = row;
                axios.get("/projectDetail/findAll").then(data => {
                    this.projectOptions = data;
                });
                axios.get("/documentCode/findAll").then(data => {
                  this.docCodeOptions = data;
                });
                this.initSuccess = true;
                this.dialogFormVisibleEdit = true;
            },
            //查看元素详情
            handleShowDetail(row) {
                this.form = {};
                this.form = row;
                this.dialogFormVisibleDetail = true;
                this.initSuccess = true;
            },
            //删除
            deleteRow(id) {
                axios.delete("/reportStatistics/delete/" + id).then(data => {
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
                for (let i = 0; i < val.length; i++) {
                    let item = {};
                    item["reportId"] = val[i].reportId;
                    item["reportName"] = val[i].reportName;
                    item["reportDetail"] = val[i].reportDetail;
                    this.downloadSelection[i] = item;
                }
            },
            // 批量删除
            deleteBatch() {
                let ids = this.multipleSelection.map(v => v.id); //[{1,username,...},{2,username,...}]=>[1,2,3]
                axios.post("/reportStatistics/deleteBatch/", ids).then(data => {
                    if (data) {
                        this.$message.success("批量删除成功");
                    } else {
                        this.$message.error("批量删除失败");
                    }
                    this.multipleSelection = [];
                    this.load()
                });
            },
            exportClick(row) {
                var content = row.reportDetail;
                var title = row.reportId + row.reportName;
                var page = '<!DOCTYPE html><html><head><meta charset="UTF-8"></head><body><h1>' + title + '</h1>' + content + '</body></html>';
                var converted = htmlDocx.asBlob(page);
                // 用 FielSaver.js里的保存方法 进行输出
                saveAs(converted, title + '.docx');
            },
            exportBatchClick() {
                if (this.downloadSelection.length < 1) {
                    alert("请勾选报表")
                } else {
                    var content = '';
                    var page = '';
                    var converted = '';
                    this.downloadSelection=sortKey(this.downloadSelection,'reportId');
                    for (let item of this.downloadSelection) {
                        var title = '<h1>' + item.reportId + item.reportName + '</h1>';
                        content += title + item.reportDetail;
                    }
                    page = '<!DOCTYPE html><html><head><meta charset="UTF-8"></head><body>' + content + '</body></html>';
                    converted = htmlDocx.asBlob(page);
                    // 用 FielSaver.js里的保存方法 进行输出
                    saveAs(converted, '报表需求.docx');
                }
                this.downloadSelection = [];
                this.load();
            }
        }
    }
    function sortKey(array,key){
        return array.sort(function(a,b){
            var x = a[key];
            var y = b[key];
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
</style>
