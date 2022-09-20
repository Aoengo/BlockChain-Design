<template>
	<div class="col-xs-offset-2 col-xs-8">
    <el-row>
      <el-col :span="24"><div class="grid-content title-position">
        <el-header>
          <div class="page-header">
            区块链投票系统
          </div>
        </el-header>
      </div>
        <br>
      </el-col>
    </el-row>
	</div>
</template>

<script>
export default {
  name:'BanNer',
}
</script>

<style scoped>
.page-header{
  font-size: 50px;
  text-align: center;
}
.title-position {
  margin:0 auto;
}
.grid-content {
  margin:0 auto;
  width:700px;
  border-radius: 4px;
  min-height: 80px;
  /*background: #B0E0E6;*/
  background: #96CDCD;

}

</style>