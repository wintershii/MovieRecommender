package tool

import redis.clients.jedis.JedisPool

object GetJedisConn {

  import org.apache.commons.pool2.impl.GenericObjectPoolConfig

  val poolConfig = new GenericObjectPoolConfig
  private lazy val pool = new JedisPool(poolConfig, "127.0.0.1", 6379)
  def getJedis(index:Int=0) ={
    //    获取连接
    val resource = pool.getResource
    //    选择库
    resource.select(index)
    //    返回工具
    resource
  }
}
