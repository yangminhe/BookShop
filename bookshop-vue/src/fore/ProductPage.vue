<template>
  <div>
    <el-row :gutter="40" style="text-align: center">
      <el-col :span="6" :offset="6">
        <el-image
          :src="bean.image"
          :lazy="true"
          fit="fill"
          style="height: 368px; width: 260px"
        />
      </el-col>
      <el-col :span="6">
        <h3>{{ bean.name }}</h3>
        <p style="color: #9d9d9d">{{ bean.author }}</p>
        <h2 style="color: #fb4e44">￥{{ bean.price }}</h2>
        <el-tag type="success">{{ bean.discount }}折</el-tag>
        <p>{{ bean.publisher }}</p>
        <el-input-number v-model="num" :min="1" :max="10" />
        <p>库存：{{ bean.stock }}件</p>
        <el-button type="warning" plain @click="handleAdd"
          >加入购物车</el-button
        >
        <el-button type="danger" @click="handleBuy">立即购买</el-button>
      </el-col>
    </el-row>
    <el-row style="text-align: center; margin-top: 50px">
      <el-col :span="12" :offset="6">
        <el-collapse>
          <el-collapse-item title="评论" style="text-align: left">
            <template slot="title">
              <i class="el-icon-copy-document" />{{
                bean.reviewCount
              }}条书友评论999
            </template>

            <div class="commentCss">
              <el-input
                type="textarea"
                :rows="2"
                placeholder="请输入评论内容"
                v-model="talking">
                </el-input>
              <el-button type="primary" @click.stop="doComentFun">发布</el-button>
            </div>

            <!-- 评论卡片start -->
            <el-card v-for="item in review" :key="item.key">
              <el-tag v-if="item.user">{{ item.user.name }}</el-tag>

              <p>{{ item.content }}</p>

              <el-tag type="info" style="text-align: right">{{
                item.createTime | formatDate
              }}</el-tag>
            </el-card>
            <!-- 评论卡片end -->
          </el-collapse-item>
        </el-collapse>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { formatDate } from "../store/formatDate";
export default {
  name: "ProductPage",
  filters: {
    formatDate(time) {
      let date = new Date(time);
      return formatDate(date, "yyyy-MM-dd hh:mm");
    },
  },
  data() {
    return {
      talking: "", // 评论内容

      uri: "product",
      commentUrl: "review/add",

      bean: [],
      review: [],
      num: 1,
      activeNames: ["1"],
    };
  },
  mounted() {
    this.getData();
  },
 
  methods: {
    getData(){
        let that = this;
        let id = that.$route.query.id;
        let url = that.uri + "/" + id;
        that.axios.get(url).then((res) => {
        let result = res.data;
        that.$nextTick(()=>{
            that.bean = result.data.product;// 书本的对象
            that.review = result.data.reviews;// 评论的数组
        });
        that.$forceUpdate();
        });
    },
    doComentFun(){// 处理评论逻辑
        let that = this;
        if(!that.talking) return;
        // api/review/add?pid=10&content=喜欢666
        let tmepTalk = that.talking;// 评论的内容
        let pId=that.bean.id;// 评论的内容
        let url = that.commentUrl + "?pid=" + pId+ "&content=" +tmepTalk;
        
        that.axios.post(url)
            .then(response =>{
                if(response.data.code === 200){
                    that.talking="";// 置空评论
                    that.getData();
                    that.$alert('评论成功','success',{
                        confirmButtonText:'确定'
                    })
                }else{
                    that.$alert('评论失败','error',{
                        confirmButtonText:'确定'
                    })
                }
            })
            .catch((err)=>{
                that.$alert('评论失败','error',{
                        confirmButtonText:'确定'
                })
            })
   
   
        },
    handleBuy() {
      if (!sessionStorage.getItem("user")) {
        this.$router.push("/login");
      } else {
        let pid = this.bean.id;
        let url = "buy?pid=" + pid + "&num=" + this.num;
        this.axios.get(url).then((response) => {
          let oiid = response.data;
          this.$router.push({
            path: "/confirm",
            query: {
              oiid: oiid,
            },
          });
        });
      }
    },
    handleAdd() {
      if (!sessionStorage.getItem("user")) {
        this.$router.push("/login");
      }
      let pid = this.bean.id;
      let url = "addCart?pid=" + pid + "&num=" + this.num;
      this.axios.get(url).then((response) => {
        let result = response.data;
        if (200 == result.code) {
          this.$message("已加入购物车");
        }
      });
    },
  },
};
</script>

<style scoped>
    .commentCss{
        text-align: right;
    }

    .commentCss .el-button--primary{
        margin-top: 10px;
        margin-bottom: 20px;
    }
</style>