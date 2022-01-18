## city-shop

### 项目背景

city-shop是一个基于Java语言开发的b2b2c的电商项目，定位是一个电商平台。项目的初衷是开发完成后打算定位到县城的商家做推广使用的，可惜最后开发到支付模块的时候项目暂停了。

### 使用的技术栈

- 基础语言：Java8
- 框架：spring boot、mybatis、mybatis-plus
- 用户权限认证：jwt
- 图片存储：阿里云OSS
- 数据存储：MySQL、Redis
- 物流信息：目前只对接了高德地图计算两个坐标点之间的距离
- 支付：计划是使用开源项目`IJPay`还未对接项目暂停。项目地址: https://javen205.gitee.io/ijpay/
- 接口文档生成：apiggs

### 项目结构

- api文档：接口文档，编译自动生成
- script：数据库脚本、部署的docker脚本
- Shop-api：controller接口
- Shop-common: 公共模块
- Shop-delivery: 物流模块
- Shop-order: 订单模块
- Shop-pay: 支付模块
- Shop-product: 商品模块
- Shop-user: 用户模块

### 说明

- 支付相关的配置在`shop-pay`模块的资源目录文件中，需要根据需要修改
- 数据库、Redis配置在`shop-api`模块的资源目录文件中，修改为自己的服务器地址
- 高德地图和阿里云OSS配置在`shop-api`模块的资源目录文件中，修改为自己的服务器地址
- 接口文档执行：`mvn clean -Dmaven.test.skip package` 自动生成
