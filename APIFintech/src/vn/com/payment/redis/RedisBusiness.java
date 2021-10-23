package vn.com.payment.redis;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.util.SerializationUtils;

import redis.clients.jedis.Jedis;
import vn.com.payment.home.SubPermissionHome;
import vn.com.payment.object.NotifyObject;
import vn.com.payment.ultities.FileLogger;
public class RedisBusiness {
	
	private static FileLogger log = new FileLogger(RedisBusiness.class);
	
	private static void test_connecttoRedis() {
		Jedis conn = null;
		try {
			conn = Pool.getConnection();
			System.out.println("Connect to Redis Success!");
		} catch (Exception e) {
			System.out.println("Connect to Redis fail!");
		} finally {
			try {
				Pool.ReleaseConnection(conn);
				System.out.println("Release off Redis Success!");
			} catch (Exception e) {
				System.out.println("Release off Redis fail!");
			}
		}
	}
	public static String getValue_fromCache(String key) {
		String temp = null;
		Jedis conn = null;
		try {
			conn = Pool.getConnection();
			temp = conn.get(key);
//			FileLogger.log(">> -----getStringValue_toCacheTime: " + key +"___Value: " + temp, LogType.REQUEST);
		} catch (Exception e) {
			log.fatal(">> getValue_fromCache -----EX: " , e);
			e.printStackTrace();
		} finally {
			try {
				Pool.ReleaseConnection(conn);
			} catch (Exception e) {
				log.fatal(">> getValue_fromCache ReleaseConnection -----EX: " , e);
				e.printStackTrace();
			}
		}
		return temp;
	}
	public static boolean setValue_toCacheTime(String key, String value, Integer expiretime) {
		Jedis conn = null;
		boolean result = false;
		try {
			log.info(">> -----setStringValue_toCacheTime: " + key +"___Value: " + value);
			conn = Pool.getConnection();
			conn.set(key, value);
			conn.expire(key, expiretime);
			result = true;
			log.info(">> -----setStringValue_toCacheTime: " + key +"___Value: " + value + "----result true");
		} catch (Exception e) {
			log.fatal(">> setValue_toCacheTime -----e: " , e);
			e.printStackTrace();
		} finally {
			try {
				Pool.ReleaseConnection(conn);
			} catch (Exception e) {
				log.fatal(">> setValue_toCacheTime -----ReleaseConnection: " , e);
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean enQueueToRedis(String key, String obj) {
		Jedis conn = null;
		try {
			conn = Pool.getConnection();
			conn.rpush(key.getBytes(), obj.getBytes(StandardCharsets.UTF_8));
			return true;
		} catch (Exception e) {
			log.fatal("enQueue ", e);
			e.printStackTrace();
		} finally {
			try {
				Pool.ReleaseConnection(conn);
				System.out.println("Release off Redis Success!");
			} catch (Exception e) {
				System.out.println("Release off Redis fail!");
			}
		}
		return false;
	}

	public static byte[] dequeueRequestFrom(String key) {
		Jedis conn = null;
		try {
			conn = Pool.getConnection();                                                                             
			return conn.lpop(key.getBytes());

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.fatal("dequeue ", e);
//			logger.fatal("queueRequestToRedis", e);
		} finally {
			try {
				Pool.ReleaseConnection(conn);
				System.out.println("Release off Redis Success!");
			} catch (Exception e) {
				System.out.println("Release off Redis fail!");
			}
		}
		return null;
	}
	
//
	public static void main(String[] args) throws Exception {
		String key = "GWMegapay";
//		String value = "1";
//		int time = 100;
//		setValue_toCacheTime(key, value, time);
		System.out.println(getValue_fromCache(key));
	}
}
