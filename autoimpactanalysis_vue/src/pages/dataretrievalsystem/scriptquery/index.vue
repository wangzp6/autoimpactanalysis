<template>
    <div>
        <div style="margin-bottom: 10px">

        </div>
        <div style="padding-bottom: 10px; padding-left: 20px;">
            <!--<el-input style="width: 200px" placeholder="请选择日期" suffix-icon="el-icon-date" class="margin-l5"></el-input>-->
            <el-input style="width: 200px" placeholder="请输入提数单编号" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="bdcid"></el-input>
            <el-input style="width: 200px" placeholder="请输入提数标题" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="bdctitle"></el-input>
            <el-input style="width: 200px" placeholder="请输入内容描述" suffix-icon="el-icon-message" class="margin-l5"
                      v-model="contentdescription"></el-input>
            <el-input style="width: 200px" placeholder="请输入提数脚本" suffix-icon="el-icon-search" class="margin-l5"
                      v-model="broomdatascript"></el-input>
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
            <el-table-column prop="bdcnumber" label="提数单编号" width="120" sortable>
            </el-table-column>
            <el-table-column prop="bdctitle" label="提数标题" width="140" sortable>
            </el-table-column>
            <el-table-column prop="contentdescription" label="内容描述" width="140" sortable>
                <template slot-scope="scope">
                    <div>{{scope.row.contentdescription}}</div>
                    <div><el-button icon="el-icon-more" class="bottomClassDetail" @click="handleShow3(scope.row)"></el-button></div>
                </template>
            </el-table-column>
            <el-table-column prop="attachmentlist" label="附件列表" width="200">
                <template slot-scope="scope">
                    <div v-for="(item,index) in scope.row.attachmentlist" :key="index">
                        <span>{{item.name}}</span>
                    </div>
                    <div><el-button icon="el-icon-more" class="bottomClassDetail" @click="handleShow1(scope.row)"></el-button></div>
                </template>
            </el-table-column>
            <el-table-column prop="broomdatascript" label="提数脚本" height="100">
                <template slot-scope="scope">
                    <div>{{scope.row.broomdatascript}}</div>
                    <div><el-button icon="el-icon-more" class="bottomClassDetail" @click="handleShow2(scope.row)"></el-button></div>
                </template>
            </el-table-column>
            <el-table-column prop="createtime" label="创建时间" width="155" sortable>
                <template slot-scope="scope">{{scope.row.createtime| dateYMDHMSFormat}}</template>
            </el-table-column>
            <el-table-column label="操作" width="240">
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
                            @confirm="deleteRow(scope.row.bdcid)">
                        <el-button type="danger" plain class="bottomClass" slot="reference">删除 <i class="el-icon-delete"></i></el-button>
                    </el-popconfirm>
                    <el-upload :action="uploadUrl+scope.row.bdcid" :show-file-list="false" :on-success="handelFileUploadSuccess" style="display: inline-block" class="margin-l5">
                        <el-button type="primary" plain class="bottomClass">上传文件 <i class="el-icon-top"></i></el-button>
                    </el-upload>
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
        <el-dialog title="脚本信息" :visible.sync="dialogFormVisible" width="50%" top="50px">
            <el-form label_witch="100px" size="small">
                <el-form-item label="提数单编号">
                    <el-input v-model="form.bdcnumber" autocomplete="off" maxlength="50"></el-input>
                </el-form-item>
                <el-form-item label="提数标题">
                    <el-input v-model="form.bdctitle" autocomplete="off" maxlength="1000"></el-input>
                </el-form-item>
                <el-form-item label="内容描述">
                    <el-input v-model="form.contentdescription" autocomplete="off" maxlength="10000"></el-input>
                </el-form-item>
                <el-form-item label="附件列表">
                    <el-input type="textarea" :disabled="true" class="textareaClass"
                              :autosize="{ minRows: 2, maxRows: 20}"
                              v-model="this.attachment" maxlength="1000"></el-input>
                </el-form-item>
                <el-form-item label="提数脚本">
                    <el-input type="textarea"
                              :autosize="{ minRows: 2, maxRows: 10}"
                              v-model="form.broomdatascript" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
        <el-dialog title="附件列表" :visible.sync="dialogFormVisibleDteail1" width="50%">
            <template>
                <el-table
                        :data="this.fileform"
                        :header-cell-style="{'text-align':'center'}">
                    <el-table-column
                            prop="name"
                            label="文件名"
                            width="550">
                    </el-table-column>
                    <el-table-column label="操作" width="150">
                        <template slot-scope="scope">
                            <el-popconfirm
                                    class="margin-l5"
                                    confirm-button-text='确定'
                                    cancel-button-text='我再想想'
                                    icon="el-icon-info"
                                    icon-color="red"
                                    title="确定删除这条个附件吗？"
                                    @confirm="deletefile(scope.row)">
                                <el-button type="danger" plain class="bottomClass" slot="reference">删除 <i class="el-icon-delete"></i></el-button>
                            </el-popconfirm>
                            <el-button type="success" plain class="bottomClass" @click="downloadfile(scope.row.url)" style="margin-left: 5px">下载<i class="el-icon-download"></i></el-button>
                        </template>
                    </el-table-column>
                </el-table>
            </template>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisibleDteail1 = false" >关 闭</el-button>
            </div>
        </el-dialog>
        <el-dialog title="提数脚本" :visible.sync="dialogFormVisibleDteail2" width="50%">
            <el-form label_witch="100px" size="small">
                <el-form-item>
                    <el-input type="textarea" :disabled="true" class="textareaClass"
                              :autosize="{ minRows: 2, maxRows: 20}"
                              v-model="form.broomdatascript"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisibleDteail2 = false" >关 闭</el-button>
            </div>
        </el-dialog>
        <el-dialog title="内容描述" :visible.sync="dialogFormVisibleDteail3" width="50%">
            <el-form label_witch="100px" size="small">
                <el-form-item>
                    <el-input type="textarea" :disabled="true" class="textareaClass"
                              :autosize="{ minRows: 2, maxRows: 20}"
                              v-model="form.contentdescription"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisibleDteail3 = false" >关 闭</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import axios from "../../../utils/http";
    import baseURL from "../../../utils/baseURL";
    export default {
        name: "Broomdatascript",
        data() {
            return {
                tableData: [],
                total: 0,
                pageNum: 1,
                pageSize: 5,
                bdcid: '',
                bdcnumber: '',
                bdctitle: "",
                contentdescription: '',
                attachmentliststr:'',
                attachmentlist:[],
                broomdatascript: '',
                createtime: '',
                form: {},
                fileform: {},
                attachment: '',
                dialogFormVisible: false,
                dialogFormVisibleDteail1: false,
                dialogFormVisibleDteail2: false,
                dialogFormVisibleDteail3: false,
                multipleSelection: [],
                headerBg: 'headerBg',
                uploadUrl:baseURL+"files/uploadFilesWithBdcid/",
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
                    "/broomdatascript/findPage", {
                        params: {
                            pageNum: this.pageNum,
                            pageSize: this.pageSize,
                            bdcid: this.bdcid,
                            bdctitle: this.bdctitle,
                            contentdescription: this.contentdescription,
                            broomdatascript: this.broomdatascript
                        }
                    }
                ).then(data => {
                    this.tableData = data.records;
                    this.total = data.total;
                });
            },
            //重置
            reset() {
                this.bdcid = "";
                this.bdctitle = "";
                this.contentdescription = "";
                this.broomdatascript = "";
                this.pageNum = 1;
                this.load()
            },
            //新增
            handleAdd() {
                this.form = {};
                this.attachment = "";
                this.dialogFormVisible = true;
            },
            //保存
            save() {
                axios.post("/broomdatascript/save", this.form).then(data => {
                    console.log(data);
                    if (data) {
                        this.$message.success("保存成功");
                        this.dialogFormVisible = false;
                        this.load()
                    } else {
                        this.$message.error("保存失败");
                        this.dialogFormVisible = false;
                        this.load()
                    }
                });
            },
            //编辑
            handleEdit(row) {
                this.form = row;
                this.dialogFormVisible = true;
                this.getattachment(row);
            },
            //查看附件列表
            handleShow1(row) {
                // this.form = row;
                this.fileform = row.attachmentlist;
                this.dialogFormVisibleDteail1 = true;
                /*this.getattachment(row);*/
            },
            //获取附件列表
            getattachment(row){
                this.attachment = '';
                console.log(row);
                for (let item of row.attachmentlist){
                    console.log(item);
                    this.attachment = this.attachment + item.name+";\r\n";
                }
                console.log(this.attachment);
            },
            //查看提数脚本
            handleShow2(row) {
                this.form = row;
                this.dialogFormVisibleDteail2 = true;
            },
            //查看内容描述
            handleShow3(row) {
                this.form = row;
                this.dialogFormVisibleDteail3 = true;
            },
            //删除
            deleteRow(bdcid) {
                axios.delete("/broomdatascript/delete/" + bdcid).then(data => {
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
                let ids = this.multipleSelection.map(v => v.bdcid); //[{1,username,...},{2,username,...}]=>[1,2,3]
                axios.post("/broomdatascript/deleteBatch/", ids).then(data => {
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
            //上传成功，回调函数
            handelFileUploadSuccess(res){
                this.$message.success("导入成功");
                this.dialogFormVisible = false;
                console.log(res);
                this.load()
            },
            //删除附件
            deletefile(row){
                console.log(row);
                axios.delete("/files/deleteWithBdcid/" + row.id).then(res => {
                    console.log(res);
                    if (res === 1) {
                        this.$message.success("删除成功");
                        this.dialogFormVisibleDteail1 = false;
                        this.load();
                    } else {
                        this.$message.error("删除失败");
                    }
                });
            },
            //下载文件
            downloadfile(url){
                window.open(url);
                this.dialogFormVisibleDteail1 = false;
            },
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