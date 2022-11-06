<template>
    <div>
        <div style="margin-bottom: 10px">

        </div>
        <div style="padding-bottom: 10px; padding-left: 20px;">
            <el-input style="width: 300px" placeholder="请输入码值" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="docCode"></el-input>
            <el-input style="width: 300px" placeholder="请输入文档类型" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="docType"></el-input>
          
            <el-button class="margin-l5" type="primary" @click="load">搜索 <i class="el-icon-search"></i></el-button>
            <el-button class="margin-l5" type="warning" @click="reset">重置 </el-button>
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
            <el-table-column prop="docCode" label="码值" width="200" sortable>
            </el-table-column>
            <el-table-column prop="docType" label="文档类型" width="300" sortable>
            </el-table-column>
            <el-table-column label="操作" width="150">
                <template slot-scope="scope">
                    <el-button type="success" plain class="bottomClass" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i>
                    </el-button>
                    <el-popconfirm
                            class="margin-l5"
                            confirm-button-text='确定'
                            cancel-button-text='我再想想'
                            icon="el-icon-info"
                            icon-color="red"
                            title="确定删除这条数据吗？"
                            @confirm="deleteRow(scope.row.codeId)">
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
        <el-dialog title="文档类型码值新增" :visible.sync="docCodeDialogAdd" width="50%" top="50px">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="docTypeForm">
                <el-form-item label="码值" prop="docCode">
                    <el-input v-model="form.docCode" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="文档类型" prop="docType" >
                    <el-input v-model="form.docType" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="docCodeDialogAdd = false">取 消</el-button>
                <el-button type="primary" @click="save()">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="文档类型码值修改" :visible.sync="docCodeDialogEdit" width="50%" top="50px">
            <el-form label_witch="100px" size="small" :model="form" :rules="rules" ref="docTypeForm">
                <el-form-item label="码值" prop="docCode">
                    <el-input v-model="form.docCode" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="文档类型" prop="docType" >
                    <el-input v-model="form.docType" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="docCodeDialogEdit = false">取 消</el-button>
                <el-button type="primary" @click="edit()">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import axios from "../../../../utils/http";

    export default {
        name: "DocTypeCode",
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 20,
                codeId:'',
                docCode: '',
                docType: "",
                form: {
                    codeId:'',
                    docCode: '',
                    docType: '',
                },
                docCodeDialogAdd: false,
                docCodeDialogEdit:false,
                multipleSelection: [],
                headerBg: 'headerBg',
                rules: {
                    docCode: [
                        { required: true, message: '请输入码值', trigger: 'blur' }
                    ],
                    docType: [
                        { required: true, message: '请输入文档类型', trigger: 'blur' }
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
                    "/documentCode/findPage", {
                        params: {
                            pageNum: this.pageNum,
                            pageSize: this.pageSize,
                            docCode: this.docCode,
                            docType: this.docType,
                        }
                    }
                ).then(data => {
                    this.tableData = data.records;
                    this.total = data.total;
                });
            },
            //重置
            reset() {
                this.codeId="";
                this.docCode = "";
                this.docType = "";
                this.pageNum = 1;
                this.load()
            },
            //新增
            handleAdd() {
                this.form = {};
                this.docCodeDialogAdd = true;
            },
            //保存
            save() {
                this.$refs['docTypeForm'].validate((valid) =>{
                    if (valid) {
                        axios.post("/documentCode/save", this.form).then(data => {
                            console.log(data);
                            if (data) {
                                this.$message.success("保存成功");
                            } else {
                                this.$message.error("保存失败");
                            }
                          this.load()
                        });
                        this.docCodeDialogAdd = false;
                    } else {
                        alert('信息填写不完整!');
                    }
                });
            },
            edit() {
                this.$refs['docTypeForm'].validate((valid) =>{
                    if (valid) {
                        axios.post("/documentCode/edit", this.form).then(data => {
                            console.log(data);
                            if (data) {
                                this.$message.success("修改成功");
                            } else {
                                this.$message.error("修改失败");
                            }
                            this.load()
                        });
                        this.docCodeDialogEdit = false;
                    } else {
                        alert('信息填写不完整!');
                    }
                });
            },
            //编辑
            handleEdit(row) {
                this.form = row;
                this.docCodeDialogEdit = true;
            },
            //删除
            deleteRow(codeId) {
                axios.delete("/documentCode/delete/" + codeId).then(data => {
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
                let codeIds = this.multipleSelection.map(v => v.codeId); //[{1,username,...},{2,username,...}]=>[1,2,3]
                axios.post("/documentCode/deleteBatch/", codeIds).then(data => {
                    console.log(data);
                    if (data) {
                        this.$message.success("批量删除成功");
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
