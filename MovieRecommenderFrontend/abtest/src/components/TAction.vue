<template>
  <el-row>
    <el-row>
      <el-col>
        <el-steps>
          <el-step title="创建实验场景" status="process"></el-step>
          <el-step title="创建实验" status="process"></el-step>
          <el-step title="创建完成" status="process"></el-step>
        </el-steps>
      </el-col>
    </el-row>

    <el-row>
      <el-col>
        <el-form>
          <el-form-item>
            <el-input size="medium" placeholder="场景标识"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input
              size="medium"
              placeholder="场景名称 规则: 名称_创建日期_创建时间"
              style="margin-top: 20px"
            ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" style="margin-top: 30px">
              下一步
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </el-row>
</template>

<script>
export default {
  name: "",
};
</script>

