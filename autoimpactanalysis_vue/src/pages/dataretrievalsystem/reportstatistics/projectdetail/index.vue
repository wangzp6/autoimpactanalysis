<template>
    <div>
        <div style="margin-bottom: 10px">

        </div>
        <div style="padding-bottom: 10px; padding-left: 20px;">
            <el-input style="width: 200px" placeholder="请输入项目编号" suffix-icon="el-icon-search" class="margin-5"
                      v-model="projectCode"></el-input>
            <el-input style="width: 200px" placeholder="请输入项目名称" suffix-icon="el-icon-search" class="margin-5"
                      v-model="projectName"></el-input>
            <el-date-picker class="margin-5"
                                 v-model="createTime"
                                 type="date"
                                value-format="yyyy-MM-dd"
                                 placeholder="选择项目创建日期"
                            >
            </el-date-picker>
            <el-date-picker class="margin-5"
                            v-model="putIntoProTime"
                            type="date"
                            value-format="yyyy-MM-dd"
                            placeholder="选择项目上线日期"
            >
            </el-date-picker>

            <el-button class="margin-5" type="primary" @click="load">搜索 <i class="el-icon-search"></i></el-button>
            <el-button class="margin-5" type="warning" @click="reset">重置 </el-button>
            <el-button class="margin-5" type="primary" @click="handleAdd">新增 <i class="el-icon-plus"></i></el-button>
            <el-popconfirm
                    class="margin-5"
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
            <el-table-column prop="projectCode" label="项目编号" width="250" sortable>
            </el-table-column>
            <el-table-column prop="projectName" label="项目名称" sortable>
            </el-table-column>
            <el-table-column prop="createTime" label="项目创建时间" width="200" sortable>
                <template slot-scope="scope">{{scope.row.createTime| dateYMDFormat}}</template>
            </el-table-column>
            <el-table-column prop="putIntoProTime" label="项目上线时间" width="200" sortable>
              <template slot-scope="scope">{{scope.row.putIntoProTime| dateYMDFormat}}</template>
            </el-table-column>
            <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                    <el-button type="success" plain class="bottomClass" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-popconfirm
                            class="margin-l10"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定删除这条数据吗？"
                            @confirm="deleteRow(scope.row.projectId)">
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
                    :page-sizes="[5, 10, 20, total]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </div>
        <el-dialog title="项目新增" :visible.sync="projectDialogAdd" width="50%" top="50px" :show-close="false"
                   :close-on-click-modal="false" :close-on-press-escape="false">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="projectForm">
                <el-form-item label="项目编号" prop="projectCode">
                    <el-input v-model="form.projectCode" autocomplete="off" maxlength="100"></el-input>
                </el-form-item>
                <el-form-item label="项目名称" prop="projectName" >
                    <el-input v-model="form.projectName" autocomplete="off" maxlength="100"></el-input>
                </el-form-item>
                <el-form-item label="项目创建时间" prop="createTime">
                    <el-date-picker
                        v-model="form.createTime"
                        type="date"
                        placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="项目上线时间" prop="putIntoProTime">
                  <el-date-picker
                      v-model="form.putIntoProTime"
                      type="date"
                      placeholder="选择日期">
                  </el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit()">取 消</el-button>
                <el-button type="primary" @click="save()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="项目修改" :visible.sync="projectDialogEdit" width="50%" top="50px" :show-close="false"
                   :close-on-click-modal="false" :close-on-press-escape="false">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="projectForm">
                <el-form-item label="项目编号" prop="projectCode">
                    <el-input v-model="form.projectCode" autocomplete="off" maxlength="100"></el-input>
                </el-form-item>
                <el-form-item label="项目名称" prop="projectName" >
                    <el-input v-model="form.projectName" autocomplete="off" maxlength="100"></el-input>
                </el-form-item>
                <el-form-item label="项目创建时间" prop="createTime">
                    <el-date-picker
                            v-model="form.createTime"
                            type="date"
                            placeholder="选择日期">
                    </el-date-picker>
                </el-form-item>
                <el-form-item label="项目上线时间" prop="createTime">
                  <el-date-picker
                      v-model="form.putIntoProTime"
                      type="date"
                      placeholder="选择日期">
                  </el-date-picker>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="quit()">取 消</el-button>
                <el-button type="primary" @click="edit()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import axios from "../../../../utils/http";

    export default {
        name: "ProjectDetail",
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 20,
                projectId: '',
                projectCode: '',
                projectName: '',
                createTime: '',
                putIntoProTime: '',
                pickerOptions: { // 限制日期不让选择今天之后的
                    disabledDate(time) {
                        return time.getTime() > Date.now();
                    }
                },
                form: {
                    projectCode: '',
                    projectName: '',
                    createTime: '',
                    putIntoProTime: '',
                },
                projectDialogAdd: false,
                projectDialogEdit:false,
                multipleSelection: [],
                headerBg: 'headerBg',
                rules: {
                    projectCode: [
                        { required: true, message: '请输入项目编号', trigger: 'blur' }
                    ],
                    projectName: [
                        { required: true, message: '请输入项目名称', trigger: 'blur' }
                    ],
                    createTime: [
                        { type: 'date', required: true, message: '请选择项目创建日期', trigger: 'blur' }
                    ],
                    putIntoProTime: [
                        { type: 'date', required: true, message: '请选择项目上线日期', trigger: 'blur' }
                    ]
                }

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
                    "/projectDetail/findPage", {
                        params: {
                            pageNum: this.pageNum,
                            pageSize: this.pageSize,
                            projectCode: this.projectCode,
                            projectName: this.projectName,
                            createTime: this.createTime,
                            putIntoProTime: this.putIntoProTime,
                        }
                    }
                ).then(data => {
                    this.tableData = data.records;
                    this.total = data.total;
                });
            },
            //重置
            reset() {
                this.projectCode = "";
                this.projectName = "";
                this.createTime = "";
                this.putIntoProTime = "";
                this.pageNum = 1;
                this.load()
            },
            //新增
            handleAdd() {
                this.form = {};
                this.projectDialogAdd = true;
            },
            //保存
            save() {
                this.$refs['projectForm'].validate((valid) =>{
                    if (valid) {
                        axios.post("/projectDetail/save", this.form).then(data => {
                            console.log(data);
                            if (data) {
                                this.$message.success("保存成功");
                            } else {
                                this.$message.error("保存失败");
                            }
                            this.load();
                        });
                        this.load();
                        this.projectDialogAdd = false;
                    } else {
                        alert('信息填写不完整!');
                    }
                });
            },
            //编辑
            handleEdit(row) {
              this.form = row;
              var createTimestamp = Date.parse(new Date(row.createTime));
              this.form.createTime=createTimestamp;
              var proTimestamp = Date.parse(new Date(row.putIntoProTime));
              this.form.putIntoProTime=proTimestamp;
              this.projectDialogEdit = true;
            },
            //修改
            edit() {
                this.$refs['projectForm'].validate((valid) =>{
                    if (valid) {
                        axios.post("/projectDetail/edit", this.form).then(data => {
                            console.log(data);
                            if (data) {
                                this.$message.success("修改成功");
                            } else {
                                this.$message.error("修改失败");
                            }
                            this.load();
                        });
                        this.load();
                        this.projectDialogEdit = false;
                    } else {
                        alert('信息填写不完整!');
                    }
                });
            },
            //退出
            quit() {
              this.form = {};
              this.projectId = '';
              this.projectCode = '';
              this.projectName = '';
              this.projectDialogAdd = false;
              this.projectDialogEdit = false;
              this.load()
            },
            //删除
            deleteRow(projectId) {
                axios.delete("/projectDetail/delete/" + projectId).then(data => {
                    if (data) {
                        this.$message.success("删除成功");
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
                let ids = this.multipleSelection.map(v => v.projectId); //[{1,username,...},{2,username,...}]=>[1,2,3]
                axios.post("/projectDetail/deleteBatch/", ids).then(data => {
                    console.log(data);
                    if (data) {
                        this.$message.success("批量删除成功");
                        this.projectDialogAdd = false;
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
</style>
