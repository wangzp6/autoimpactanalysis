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
            <el-date-picker class="margin-l5"
                            v-model="createTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择项目创建日期"
                            :picker-options="pickerOptions">
            </el-date-picker>
            <el-button class="margin-l5" type="primary" @click="load">搜索</el-button>
            <el-button class="margin-l5" type="warning" @click="reset">重置</el-button>
        </div>
        <div style="padding-left: 25px; padding-bottom: 10px">
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
                  height="690"
                  :header-cell-style="{'text-align':'center'}"
                  :row-style="{height: '0'}"
                  :cell-style="{padding: '0'}"
                  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"></el-table-column>
            <el-table-column prop="reportId" label="报表编号" width="150" sortable>
            </el-table-column>
            <el-table-column prop="reportName" label="报表名称" width="350" sortable>
            </el-table-column>
            <el-table-column prop="projectId" label="项目编号" width="200" sortable>
            </el-table-column>
            <el-table-column prop="projectName" label="项目名称" width="350" sortable>
            </el-table-column>
            <el-table-column prop="operationFlag" label="操作标志" width="110" sortable>
            </el-table-column>
            <el-table-column prop="createTime" label="项目创建时间" width="120" sortable>
            </el-table-column>
            <el-table-column label="操作" width="230">
                <template slot-scope="scope">
                    <el-button type="success" plain class="bottomClass" @click="handleView(scope.row)">查看 <i class="el-icon-view"></i>
                    </el-button>
                    <el-button type="success" plain class="bottomClass" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-popconfirm
                            class="margin-l10"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定删除这条数据吗？"
                            @confirm="deleteRow(scope.row.id)">
                        <el-button type="danger" plain class="bottomClass" slot="reference">删除 <i class="el-icon-delete"></i></el-button>
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
        <el-dialog title="报表明细" :visible.sync="dialogFormVisible" width="50%" top="50px">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="reportForm">
                <el-form-item label="项目名称" prop="projectName">
                <el-select v-model="form.projectId" clearable placeholder="请选择">
                    <el-option
                            v-for="item in projectOptions"
                            :key="item.projectId"
                            :label="item.projectName"
                            :value="item.projectId">
                    </el-option>
                </el-select>
                </el-form-item>
                <el-form-item label="报表编号" prop="reportId">
                    <el-input v-model="form.reportId" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="报表名称" prop="reportName">
                    <el-input v-model="form.reportName" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="操作标志" prop="operationFlag">
                    <el-select v-model="form.operationFlag" placeholder="I:新增；U:修改">
                        <el-option
                                v-for="item in operationFlagOptions"
                                :key="item.operationFlag"
                                :label="item.label"
                                :value="item.operationFlag">
                        </el-option>
                    </el-select>
                </el-form-item>
                <!--<el-tree :data="itemDetailsList" :props="defaultProps" @node-click="handleNodeClick"></el-tree>-->
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="报表元素" :visible.sync="dialogFormVisibleItem" width="50%" top="50px" :show-close="false">
            <el-form label_witch="100px" size="small">
                <el-form-item label="报表名称">
                    <el-input v-model="form.reportName" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="元素明细">
                    <el-button class="margin-l5" type="primary" @click="downloadFile">报表导出</el-button>
                    <div v-for="(record, index) in itemDetailsList">
                        <br/>
                        <div v-if="record.itemLevel == '1'" style="text-indent: 0px">
                            <el-input v-model="record.itemTitle" size="small" maxlength="50" style="width: 30px; font-size:20px" class="borderNone"></el-input>
                            <el-input v-model="record.itemName" maxlength="50" style="width: 600px; font-size:20px" class="borderNone"></el-input>
                        </div>
                        <div v-else-if="record.itemLevel == '2'" style="text-indent: 10px">
                            <el-input v-model="record.itemTitle" size="small" maxlength="50" style="width: 42px; font-size:19px" class="borderNone"></el-input>
                            <el-input v-model="record.itemName" maxlength="50" style="width: 600px; font-size:19px" class="borderNone"></el-input>
                        </div>
                        <div v-else-if="record.itemLevel == '3'" style="text-indent: 20px">
                            <el-input v-model="record.itemTitle" size="small" maxlength="50" style="width: 55px; font-size:18px" class="borderNone"></el-input>
                            <el-input v-model="record.itemName" maxlength="50" style="width: 600px; font-size:18px" class="borderNone"></el-input>
                        </div>
                        <div v-else-if="record.itemLevel == '4'" style="text-indent: 30px">
                            <el-input v-model="record.itemTitle" size="small" maxlength="50" style="width: 70px; font-size:17px" class="borderNone"></el-input>
                            <el-input v-model="record.itemName" maxlength="50" style="width: 600px; font-size:17px" class="borderNone"></el-input>
                        </div>
                        <div v-else-if="record.itemLevel == '5'" style="text-indent: 40px">
                            <el-input v-model="record.itemTitle" size="small" maxlength="50" style="width: 80px; font-size:16px" class="borderNone"></el-input>
                            <el-input v-model="record.itemName" maxlength="50" style="width: 600px; font-size:16px" class="borderNone"></el-input>
                    </div>
                        <div v-else-if="record.itemLevel == '6'" style="text-indent: 50px">
                            <el-input v-model="record.itemTitle" size="small" maxlength="50" style="width: 88px; font-size:15px" class="borderNone"></el-input>
                            <el-input v-model="record.itemName" maxlength="50" style="width: 600px; font-size:15px" class="borderNone"></el-input>
                    </div>
                        <div v-else style="text-indent: 0px">
                            <el-input v-model="record.itemTitle" size="small" maxlength="50" style="width: 100px; font-size:14px" class="borderNone"></el-input>
                            <el-input v-model="record.itemName" maxlength="50" style="width: 600px; font-size:14px" class="borderNone"></el-input>
                        </div>
                            <TinymceEditor v-if="initSuccess" :content.sync="record.itemDetail"
                                           :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
                    </div>
                </el-form-item>
                <!--<el-tree :data="itemDetailsList" :props="defaultProps" @node-click="handleNodeClick"></el-tree>-->
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">关 闭</el-button>
                <!--<el-button type="primary" @click="save">确 定</el-button>-->
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import axios from "../../../../utils/http";
    // import child from "index.vue";
    export default {
        name: "ReportDetail",
        mounted(){

        },
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 20,
                id: '',
                reportId: '',
                reportName: '',
                operationFlag: '',
                projectId: '',
                projectName: '',
                createTime: '',
                pickerOptions: { // 限制出生日期不让选择今天之后的
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                rules: {
                    projectId: [
                        { required: true, message: '请选择项目名称', trigger: 'blur' }
                    ],
                    reportId: [
                        { required: true, message: '请输入报表编号', trigger: 'blur' }
                    ],
                    reportName: [
                        { required: true, message: '请输入报表名称', trigger: 'blur' }
                    ],
                        operationFlag: [
                        { required: true, message: '请输入操作标志', trigger: 'blur' }
                    ]
                },
                projectOptions:[],
                operationFlagOptions: [{
                    operationFlag: 'I',
                    label: 'I'
                }, {
                    operationFlag: 'U',
                    label: 'U'
                }],
                itemDetailsList:[],
                initSuccess:false,
                form: {
                    projectId:'',
                    reportId:'',
                    reportName:'',
                    operationFlag:'',
                },
                fileForm: {},
                dialogFormVisible: false,
                dialogFormVisibleItem: false,
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
                this.projectId = '';
                this.projectName = '';
                this.form.reportId='';
                this.form.projectName='';
                this.load()
            },
            //新增
            handleAdd() {
                this.form = {};
                this.projectOptions = [];
                this.projectId = '';
                this.projectName = '';
                axios.get("/projectDetail/findAll").then(data => {
                    console.log(data);
                    this.projectOptions = data;
                });
                this.dialogFormVisible = true;
                this.initSuccess = true;
            },
            //保存
            save() {
                this.$refs['reportForm'].validate((valid) => {
                    if (valid) {
                        axios.post("/reportStatistics/save", this.form).then(data => {
                            console.log(data);
                            if (data) {
                                this.$message.success("保存成功");

                            } else {
                                this.$message.error("保存失败");
                            }

                        });
                        this.dialogFormVisible = false;
                        this.load()
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
                this.form = row;
                axios.get("/itemDetailList/getItemByReportId/" + row.reportId).then(data => {
                    console.log(data);
                    this.itemDetailsList = data;
                });
                this.dialogFormVisibleItem = true;
                this.initSuccess = true;
            },
            quit(){
                this.form = {};
                console.log("编辑form："+this.form);
                this.itemDetailsList = [];
                console.log("编辑detail："+this.itemDetailsList );
                this.dialogFormVisibleItem = false;
                this.initSuccess = false;
                this.projectOptions = [];
                this.projectId = '';
                this.projectName = '';
            },
            //编辑
            handleEdit(row) {
                this.form = row;
                axios.get("/projectDetail/findAll").then(data => {
                    console.log(data);
                    this.projectOptions = data;
                });
                this.initSuccess = true;
                this.dialogFormVisible = true;

            },
            //删除
            deleteRow(id) {
                axios.delete("/reportStatistics/delete/" + id).then(data => {
                    if (data) {
                        this.$message.success("删除成功");
                        this.dialogFormVisible = false;
                        this.load()
                    } else {
                        this.$message.error("删除失败");
                    }
                });
            },
            // 多选
            handleSelectionChange(val) {
                this.multipleSelection = val //[{1,username,...},{2,username,...}]
            },
            // 批量删除
            deleteBatch() {
                let ids = this.multipleSelection.map(v => v.id); //[{1,username,...},{2,username,...}]=>[1,2,3]
                axios.post("/reportStatistics/deleteBatch/", ids).then(data => {
                    console.log(data);
                    if (data) {
                        this.$message.success("批量删除成功");
                        this.dialogFormVisible = false;
                        this.load()
                    } else {
                        this.$message.error("批量删除失败");
                    }
                });
            }
        }
    }
</script>

<style lang="scss">
    .headerBg {
        background: #F5F5F5 !important;
    }
    /*    .tableClass{
            td{
                padding: 0 !important;
                height: 30px;
                line-height: 30px;
            }
            th{
                padding: 0 !important;
                height: 30px;
                line-height: 30px;
            }
        }*/
    .tableClass .cell{
        max-height: 125px;
        line-height: 25px!important;
        /*text-align: left;*/
    }
    .bottomClass{
        padding: 5px!important;
    }
    .bottomClassDetail{
        padding: 5px!important;
        right: 2px;
        bottom: 2px;
        position: absolute;
        border: none;
    }
    .el-dialog__body {
        padding: 10px 20px!important;
    }
    .el-textarea.is-disabled .el-textarea__inner{
        color: #000000!important;
    }
    .borderNone input.el-input__inner {
        border: none ;
        box-shadow: none !important;
        padding: 0px;
    }
</style>
