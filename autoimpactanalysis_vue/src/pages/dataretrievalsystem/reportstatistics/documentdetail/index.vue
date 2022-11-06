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
            <el-table-column prop="docState" label="文档状态" width="150" :formatter="docStateFormat" :filters="docStateOptions" :filter-method="filterDocState" sortable>
            </el-table-column>
            <el-table-column prop="docCode" label="文档类型" width="150" :formatter="docTypeFormat" :filters="docTypeOptions" :filter-method="filterDocType" sortable>
            </el-table-column>
            <el-table-column prop="operator" label="操作人" width="110" sortable>
            </el-table-column>
            <el-table-column prop="docDetail" label="文档明细" width="110" class="downloadWord">
                <template slot-scope="scope">
                    <div>{{scope.row.docDetail}}</div>
                    <div>
                        <el-button icon="el-icon-more" class="bottomClassDetail"
                                   @click="handleShowDetail(scope.row)"></el-button>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="380">
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
                        @confirm="editDocState(scope.row)">
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
        <el-dialog title="文档新增" :visible.sync="docDetailDialogAdd" width="70%" top="10px" style="margin-top: 0px"
                   :show-close="false">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="docDetailForm">
                <el-form-item label="项目名称" prop="projectId">
                    <el-select v-model="form.projectId" placeholder="请选择项目名称" filterable default-first-option
                               style="width: 600px;" @change="getReportListByProjectIdAdd(form.projectId)">
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
                <el-form-item label="文档类型" prop="docCode">
                  <el-select v-model="form.docCode">
                    <el-option
                        v-for="item in docTypeOptions"
                        :key="item.docCode"
                        :label="item.docCode+'-'+item.docType"
                        :value="item.docCode">
                    </el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="文档内容">
                    <br>
                    <TinymceEditor v-if="initSuccess" :content.sync="form.docDetail"
                                   :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="文档编辑" :visible.sync="docDetailDialogEdit" width="70%" top="10px" style="margin-top: 0px"
                   :show-close="false">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="docDetailForm">
              <el-form-item label="项目名称" prop="projectId">
                <el-select v-model="form.projectId" placeholder="请选择项目名称" filterable default-first-option
                           style="width: 600px;" @change="getReportListByProjectIdEdit(form.projectId)">
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
                <el-form-item label="文档类型" prop="docCode">
                    <el-select v-model="form.docCode">
                        <el-option
                            v-for="item in docTypeOptions"
                            :key="item.docCode"
                            :label="item.docCode+'-'+item.docType"
                            :value="item.docCode">
                        </el-option>
                    </el-select>
                </el-form-item>
                    <el-form-item label="文档内容">
                        <br>
                        <TinymceEditor v-if="initSuccess" :content.sync="form.docDetail"
                                       :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
                    </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">取 消</el-button>
                <el-button type="primary" @click="edit">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="文档查看" :visible.sync="docDetailDialogView" width="70%" top="10px" style="margin-top: 0px"
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
<!--                  <el-form-item label="文档状态" prop="docState">-->
                    文档状态:
                    <el-select v-model="form.docState" class="noBorderSelect" disabled style="margin-right: 20px">
                      <el-option
                          v-for="item in docStateOptions"
                          :key="item.docState"
                          :label="item.label"
                          :value="item.docState">
                      </el-option>
                    </el-select>
<!--                  </el-form-item>-->
                    文档类型:
                    <el-input v-model="form.docType" autocomplete="off" :disabled="true" style="width: 300px;"
                              class="borderNone"></el-input>
                </div>
                <div class="inputCLass">
                    操作人:
                    <el-input v-model="form.operator" autocomplete="off" :disabled="true" style="width: 600px;"
                              class="borderNone"></el-input>
                </div>
                文档内容:
                <br>
                <TinymceNoEdit v-if="initSuccess" :content.sync="form.docDetail"
                               :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">关 闭</el-button>
            </div>
        </el-dialog>
        <el-dialog title="文档明细" :visible.sync="docDetailDialogDetail" width="70%" top="10px" style="margin-top: 0px"
                   :show-close="false">
            <TinymceView v-if="initSuccess" :content.sync="form.docDetail" :disabled="true"/>
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
        name: "DocumentDetail",
        mounted() {

        },
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 10,
                docId: '',
                docCode: '',
                docType: '',
                docState: '',
                docDetail: '',
                operator: '',
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
                    docType: [
                        {required: true, message: '请输入文档类型', trigger: 'blur'}
                    ]
                },
                projectOptions: [],
                reportOptions: [],
                docTypeOptions: [],
                docStateOptions: [{
                    docState: '0',
                    label: '未定稿',
                    text:'未定稿',
                    value:'0'
                }, {
                    docState: '1',
                    label: '已定稿',
                    text:'已定稿',
                    value:'1'
                }],
                initSuccess: false,
                form: {
                    docCode: '',
                    docType: '',
                    docState: '',
                    reportId: '',
                    reportCode: '',
                    reportName: '',
                    projectId: '',
                    projectCode: '',
                    projectName: '',
                },
                docDetailDialogAdd: false,
                docDetailDialogEdit: false,
                docDetailDialogView: false,
                docDetailDialogDetail: false,
                multipleSelection: [],
                headerBg: 'headerBg',
                downloadSelection: [],
            }
        },
        created() {
            this.load()
            this.getDocTypeList();
            this.getProjectList();
            // this.getReportList();
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
            getReportListByProjectIdAdd(projectId) {
                axios.get("/reportDetail/getByProjectId/" + projectId).then(data => {
                    console.log("getReportListByProjectIdAdd:"+projectId)
                    console.log(data);
                    this.reportOptions = data;
                    console.log(this.reportOptions);
              });
            },
            getReportListByProjectIdEdit(projectId) {
              this.form.reportId='';
              this.form.reportCode='';
              this.form.reportName='';
              axios.get("/reportDetail/getByProjectId/" + projectId).then(data => {
                console.log("getReportListByProjectIdEdit:"+projectId)
                console.log(data);
                this.reportOptions = data;
                console.log(this.reportOptions);
              });
            },
            // getReportList() {
            //   axios.get("/reportDetail/findAll/").then(data => {
            //     console.log("getReportList:")
            //     console.log(data);
            //     this.reportOptions = data;
            //     console.log(this.reportOptions);
            //   });
            // },
            getDocTypeList() {
                axios.get("/documentCode/findAll").then(data => {
                    console.log("getDocTypeList")
                    this.docTypeOptions=[];
                    for (let i = 0; i < data.length; i++) {
                        let item = {};
                        item["docCode"] = data[i].docCode;
                        item["docType"] = data[i].docType;
                        item["label"] = data[i].docType;
                        item["text"] = data[i].docType;
                        item["value"] = data[i].docCode;
                        this.docTypeOptions[i] = item;
                    }
                });
            },
            docStateFormat(row){
              return row.docState == '1' ? '已定稿' : '未定稿';
            },
            filterDocState(value, row){
              return row.docState === value;
            },
            docTypeFormat(row){
              for (let i = 0; i < this.docTypeOptions.length; i++) {
                if(row.docCode === this.docTypeOptions[i].docCode) {
                  return this.docTypeOptions[i].docType;
                }
              }
            },
            filterDocType(value, row){
              return row.docCode === value;
            },
            // 加载
            load() {
                axios.get(
                    "/documentDetail/findPage", {
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
                this.initSuccess = false;
                this.load()
            },
            //新增
            handleAdd() {
                this.form = {};
                this.docDetail = '';
                this.getProjectList();
                this.getDocTypeList();
                this.docDetailDialogAdd = true;
                this.initSuccess = true;
            },
            //保存
            save() {
                this.$refs['docDetailForm'].validate((valid) => {
                    if (valid) {
                        let userVO = JSON.parse(localStorage.getItem("user"));
                        this.form.operator=userVO.username;
                        axios.post("/documentDetail/save", this.form).then(data => {
                            if (data) {
                                this.$message.success("保存成功");
                            } else {
                                this.$message.error("保存失败");
                            }
                            this.load()
                        });
                        this.docDetailDialogAdd = false;
                        this.initSuccess = false;
                    } else {
                        alert('信息填写不完整!');
                    }
                })
            },
            //编辑
            handleEdit(row) {
              if("1"!=row.docState){
                this.docDetail = '';
                this.getProjectList();
                this.getReportListByProjectIdEdit(row.projectId);
                this.getDocTypeList();
                this.form = row;
                let userVO = JSON.parse(localStorage.getItem("user"));
                this.form.operator = this.form.operator+"|"+userVO.username;
                this.initSuccess = true;
                this.docDetailDialogEdit = true;
              }else{
                alert("已定稿，不能编辑！")
              }
            },
            //修改
            edit() {
                this.$refs['docDetailForm'].validate((valid) =>{
                    if (valid) {
                        axios.post("/documentDetail/edit", this.form).then(data => {
                            console.log(data);
                            if (data) {
                                this.$message.success("修改成功");
                            } else {
                                this.$message.error("修改失败");
                            }
                            this.load()
                        });
                        this.initSuccess = false;
                        this.docDetailDialogEdit = false;
                    } else {
                        alert('信息填写不完整!');
                    }
              });
            },
            //定稿
            editDocState(row){
              if("1" != row.docState){
                this.form = row;
                let userVO = JSON.parse(localStorage.getItem("user"));
                this.form.operator = this.form.operator+"|"+userVO.username;
                axios.post("/documentDetail/editDocState", this.form).then(data => {
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
            //查看
            handleView(row) {
                this.form = {};
                this.form = row;
                this.form.docDetail = row.docDetail;
                this.docDetailDialogView = true;
                this.initSuccess = true;
            },
            quit() {
                this.form = {};
                this.reportId = "";
                this.reportCode = "";
                this.reportName = "";
                this.projectId = "";
                this.projectCode = "";
                this.projectName = "";
                this.docCode = '';
                this.docType = '';
                this.form.docDetail = '';
                this.docDetailDialogAdd = false;
                this.docDetailDialogEdit = false;
                this.docDetailDialogView = false;
                this.docDetailDialogDetail = false;
                this.initSuccess = false;
                this.load()
            },
            //查看元素详情
            handleShowDetail(row) {
                this.form = {};
                this.form = row;
                this.docDetailDialogDetail = true;
                this.initSuccess = true;
            },
            //删除
            deleteRow(row) {
              if("1" != row.docState){
                axios.delete("/documentDetail/delete/" + row.docId).then(data => {
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
                    item["docType"] = val[i].docType;
                    item["docDetail"] = val[i].docDetail;
                    this.downloadSelection[i] = item;
                }
            },
            // 批量删除
            deleteBatch() {
              let docStates = this.multipleSelection.map(v => v.docState); //[{1,username,...},{2,username,...}]=>[1,2,3]
              let flag = false;
              for (let i = 0; i < docStates.length; i++) {
                if("1" === docStates[i]){
                  flag = true;
                  break;
                }
              }
              if(flag===false){
                let docIds = this.multipleSelection.map(v => v.docId); //[{1,username,...},{2,username,...}]=>[1,2,3]
                axios.post("/documentDetail/deleteBatch/", docIds).then(data => {
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
                let content = row.docDetail;
                let title = row.reportCode + row.reportName + row.docType;
                let page = '<!DOCTYPE html><html><head><meta charset="UTF-8"></head><body><h1>' + title + '</h1>' + content + '</body></html>';
                let converted = htmlDocx.asBlob(page);
                // 用 FielSaver.js里的保存方法 进行输出
                saveAs(converted, title + '.docx');
            },
            exportBatchClick() {
                if (this.downloadSelection.length < 1) {
                    alert("请勾选报表")
                } else {
                    let content = '';
                    let page = '';
                    let converted = '';
                    let title ='';
                    this.downloadSelection=sortKey(this.downloadSelection,'reportCode');
                    for (let item of this.downloadSelection) {
                        title = '<h1>' + item.reportCode + item.reportName + item.docType+'</h1>';
                        content += title + item.docDetail;
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
