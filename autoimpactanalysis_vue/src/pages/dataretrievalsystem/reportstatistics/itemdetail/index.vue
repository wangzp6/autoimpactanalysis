<template>
    <div>
        <div style="margin-bottom: 10px">

        </div>
        <div style="padding-bottom: 10px; padding-left: 20px;">
            <el-input style="width: 200px" placeholder="请输入报表编号" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="reportId"></el-input>
            <el-input style="width: 200px" placeholder="请输入报表名称" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="reportName"></el-input>
            <el-button class="margin-l5" type="primary" @click="load">搜索</el-button>
            <el-button class="margin-l5" type="warning" @click="reset">重置</el-button>
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
            <el-table-column prop="reportName" label="报表名称" width="300" sortable>
            </el-table-column>
            <el-table-column prop="itemTitle" label="元素标题" width="110" sortable>
            </el-table-column>
            <el-table-column prop="itemName" label="元素名称" width="300" sortable>
            </el-table-column>
            <el-table-column prop="itemLevel" label="元素级别" width="110" sortable>
            </el-table-column>
            <el-table-column prop="itemDetail" label="元素明细" width="400">
                <template slot-scope="scope">
                    <div>{{scope.row.itemDetail}}</div>
                    <div>
                        <el-button icon="el-icon-more" class="bottomClassDetail"
                                   @click="handleShowDetail(scope.row)"></el-button>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                    <el-button type="success" plain class="bottomClass" @click="handleEdit(scope.row)">编辑 <i
                            class="el-icon-edit"></i>
                    </el-button>
                    <el-popconfirm
                            class="margin-l5"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定删除这条数据吗？"
                            @confirm="deleteRow(scope.row.id)">
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
        <el-dialog title="项目明细" :visible.sync="dialogFormVisible" width="50%" top="50px" :show-close="false">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="itemForm">
                <el-form-item label="项目名称" prop="projectName">
                    <el-select v-model="form.projectId" @change="getReportList" placeholder="请选择">
                        <el-option
                                v-for="item in projectOptions"
                                :key="item.projectId"
                                :label="item.projectName"
                                :value="item.projectId">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="报表名称" prop="reportName">
                    <el-select v-model="form.reportId" placeholder="请选择">
                        <el-option
                                v-for="item in reportOptions"
                                :key="item.reportId"
                                :label="item.reportName"
                                :value="item.reportId">
                        </el-option>
                    </el-select>

                </el-form-item>
                <el-form-item label="元素标题" prop="itemTitle">
                    <el-input v-model="form.itemTitle" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="元素名称" prop="itemName">
                    <el-input v-model="form.itemName" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="元素级别" prop="itemLevel">
                    <el-select v-model="form.itemLevel" placeholder="请选择">
                        <el-option
                                v-for="item in levelOptions"
                                :key="item.itemLevel"
                                :label="item.label"
                                :value="item.itemLevel">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="元素序号">
                    <el-input v-model="form.itemOrder" autocomplete="off" maxlength="50" @change="confirmOrder"></el-input>
                </el-form-item>
                <el-form-item label="元素明细">
                    <br>
                    <TinymceEditor v-if="initSuccess" :content.sync="form.itemDetail"
                                   :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="项目明细" :visible.sync="dialogFormVisible2" width="50%" top="50px" :show-close="false">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="itemForm">
                <el-form-item label="报表名称" prop="reportName">
                    <el-select v-model="form.reportId" placeholder="请选择">
                        <el-option
                                v-for="item in reportOptions"
                                :key="item.reportId"
                                :label="item.reportName"
                                :value="item.reportId">
                        </el-option>
                    </el-select>

                </el-form-item>
                <el-form-item label="元素标题" prop="itemTitle">
                    <el-input v-model="form.itemTitle" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="元素名称" prop="itemName">
                    <el-input v-model="form.itemName" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="元素级别" prop="itemLevel">
                    <el-select v-model="form.itemLevel" placeholder="请选择">
                        <el-option
                                v-for="item in levelOptions"
                                :key="item.itemLevel"
                                :label="item.label"
                                :value="item.itemLevel">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="元素序号">
                    <el-input v-model="form.itemOrder" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="元素明细">
                    <br>
                    <TinymceEditor v-if="initSuccess" :content.sync="form.itemDetail"
                                   :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="元素明细" :visible.sync="dialogFormVisibleDetail" width="50%" :show-close="false">
            <el-form label_witch="100px" size="small">
                <el-form-item>
                    <br>
                    <TinymceNoEdit v-if="initSuccess" :content.sync="form.itemDetail"
                                   :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit">关 闭</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import axios from "../../../../utils/http";

    export default {
        name: "ItemDetail",
        /*focusSelectValue() {
            console.log(this.$refs.projectId);
            this.$refs.projectId.$refs.input.blur = () => {
                console.log(0);
            };
        },*/
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 20,
                pageSizeList: [10, 20, 30],
                reportId: '',
                reportName: '',
                reportOptions: [],
                projectId: '',
                projectName: '',
                projectOptions: [],
                id: '',
                itemTitle: '',
                itemName: '',
                itemLevel: '',
                itemOrder: '',
                itemDetail: '',
                levelOptions: [{
                    itemLevel: '1',
                    label: '1'
                }, {
                    itemLevel: '2',
                    label: '2'
                }, {
                    itemLevel: '3',
                    label: '3'
                }, {
                    itemLevel: '4',
                    label: '4'
                }, {
                    itemLevel: '5',
                    label: '5'
                }, {
                    itemLevel: '6',
                    label: '6'
                }],
                form: {},
                rules: {
                    projectId: [
                        {required: true, message: '请选择项目名称', trigger: 'blur'}
                    ],
                    reportId: [
                        {required: true, message: '请选择报表名称', trigger: 'blur'}
                    ],
                    itemTitle: [
                        {required: true, message: '请输入元素标题', trigger: 'blur'}
                    ],
                    itemName: [
                        {required: true, message: '请输入元素名称', trigger: 'blur'}
                    ],
                    itemLevel: [
                        {required: true, message: '请输入元素级别', trigger: 'blur'}
                    ]
                },
                dialogFormVisible: false,
                dialogFormVisible2: false,
                dialogFormVisibleDetail: false,
                initSuccess: false,
                multipleSelection: [],
                headerBg: 'headerBg',
            }
        },
        created() {
            this.load();
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
                    "/itemDetailList/findPage", {
                        params: {
                            pageNum: this.pageNum,
                            pageSize: this.pageSize,
                            reportId: this.reportId,
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
                this.pageNum = 1;
                this.load()
            },
            //新增
            handleAdd() {
                this.form = {};
                this.projectOptions = this.getProjectList();
                this.reportOptions = [];
                this.dialogFormVisible = true;
                this.initSuccess = true;
            },
            //保存
            save() {
                this.$refs['itemForm'].validate((valid) => {
                    if (valid) {
                        axios.post("/itemDetailList/save", this.form).then(data => {
                            console.log(data);
                            if (data) {
                                this.$message.success("保存成功");
                            } else {
                                this.$message.error("保存失败");
                            }
                            this.load();
                        });
                        this.dialogFormVisible = false;
                        this.dialogFormVisible2 = false;
                        this.initSuccess = false;
                    } else {
                        alert('信息填写不完整!');
                    }
                });
            },
            quit() {
                this.form = {};
                this.projectOptions = [];
                this.reportOptions = [];
                this.itemDetail = '';
                this.initSuccess = false;
                this.dialogFormVisible = false;
                this.dialogFormVisible2 = false;
                this.dialogFormVisibleDetail = false;
            },
            getProjectList() {
                axios.get("/projectDetail/findAll").then(data => {
                    console.log(data);
                    this.projectOptions = data;
                });
            },
            getReportList() {
                axios.get("/reportStatistics/getByProjectId/" + this.form.projectId).then(data => {
                    console.log(data);
                    this.reportOptions = data;
                });
            },
            handleEdit(row) {
                this.projectOptions = this.getProjectList();
                this.reportOptions = [];
                this.itemDetail = '';
                this.projectId = this.form.projectId;
                this.projectName = this.form.projectName;
                this.form = row;
                this.initSuccess = true;
                this.dialogFormVisible2 = true;
            },
            //删除
            deleteRow(id) {
                axios.delete("/itemDetailList/delete/" + id).then(data => {
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
                axios.post("/itemDetailList/deleteBatch/", ids).then(data => {
                    console.log(data);
                    if (data) {
                        this.$message.success("批量删除成功");
                        this.dialogFormVisible = false;
                        this.load()
                    } else {
                        this.$message.error("批量删除失败");
                    }
                });
            },
            //查看元素详情
            handleShowDetail(row) {
                this.form = {};
                this.form = row;
                this.dialogFormVisibleDetail = true;
                this.initSuccess = true;
            },
            confirmOrder(){
                if (!/^1[0-9]{10}$/.test(this.form.itemOrder))
                    this.form.itemOrder = '';
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
    .tableClass .cell {
        max-height: 125px;
        line-height: 25px !important;
        /*text-align: left;*/
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
</style>
