<template>
    <div class="child">
        <el-form label_witch="100px" size="small">
            <el-input v-model="itemTitle" autocomplete="off" maxlength="50"></el-input>
            <el-input v-model="itemName" autocomplete="off" maxlength="50"></el-input>
            <TinymceEditor v-if="initSuccess" :content.sync="itemDetail"
                           :autosize="{ minRows: 2, maxRows: 20}" :disabled="true"/>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "child",
        props: ["itemTitle", "itemName", "itemDetail"],
        data() {
            return {};
        },
    }
</script>

<style scoped>

</style>