package com.xly.utils;/**
 * @author DELL
 * @date 2018/5/29
 */

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * @author DELL
 * @date 2018/5/29 9:22
 **/
public class PasswordHash {

  private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

  // The following constants may be changed without breaking existing hashes.

  /**
   * 盐值长度
   */
  private static final int SALT_BYTE_SIZE = 24;
  /**
   * 加密结果长度
   */
  private static final int HASH_BYTE_SIZE = 24;
  /**
   * 加密迭代次数
   */
  private static final int PBKDF2_ITERATIONS = 1000;

  // 加密后的输出结果按":"分割后的对应的下标 为固定值不可更改
  // 加密输出结果示例 --> 迭代次数:盐值:hash值

  private static final int ITERATION_INDEX = 0;
  private static final int SALT_INDEX = 1;
  private static final int PBKDF2_INDEX = 2;

  /**
   * 对密码进行加盐的hash
   *
   * @param password 要加密的密码字符串
   * @return 一个加盐的PBKDF2哈希算法处理的密码
   */
  public static String createHash(String password) {

    // 生成一个随机数做盐值
    SecureRandom random = new SecureRandom();
    // 设定盐值长度
    byte[] salt = new byte[SALT_BYTE_SIZE];
    random.nextBytes(salt);

    // hash加密
    byte[] hash = hashPBKDF2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
    // 格式   迭代次数:盐值:hash值
    return PBKDF2_ITERATIONS + ":" + toHex(salt) + ":" + toHex(hash);
  }


  /**
   * 验证密码
   *
   * @param password 要验证的密码
   * @param correctHash 对应的hash结果
   * @return true if the password is correct, false if not
   */
  public static boolean validatePassword(String password, String correctHash) {
    String[] params = correctHash.split(":");
    // 获取加密时的迭代次数
    int iterations = Integer.parseInt(params[ITERATION_INDEX]);
    // 获取加密时的盐值
    byte[] salt = fromHex(params[SALT_INDEX]);
    // 获取加密时的结果
    byte[] hash = fromHex(params[PBKDF2_INDEX]);
    // 对要验证的密码使用同样的迭代次数、盐值和加密结果长度进行加密
    byte[] testHash = hashPBKDF2(password, salt, iterations, hash.length);
    // true为密码正确
    return slowEquals(hash, testHash);
  }

  /**
   * 判断两个字节数组是否相等
   *
   * @param a 第一个字节数组
   * @param b 第二个字节数组
   * @return 若字节数组相等则返回true，否则返回false
   */
  private static boolean slowEquals(byte[] a, byte[] b) {
    // 通过异或比较两个数组的长度
    int diff = a.length ^ b.length;
    // 通过异或比较内容,存在不一致时diff的值会大于0
    for (int i = 0; i < a.length && i < b.length; i++) {
      diff |= a[i] ^ b[i];
    }
    return diff == 0;
  }

  /**
   * PBKDF2 哈希算法加密密码
   *
   * @param password 要进行哈希加密的密码
   * @param salt 盐值
   * @param iterations 迭代技术
   * @param bytes 导出密钥长度
   * @return 经过PBDKF2方式加密的密码
   */
  private static byte[] hashPBKDF2(String password, byte[] salt, int iterations, int bytes) {
    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, bytes * 8);
    SecretKeyFactory skf;
    byte[] result = null;
    try {
      // 使用 PBKDF2WithHmacSHA1 加密算法
      skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
      result = skf.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 将十六进制字符串转换为byte数组
   *
   * @param hex 十六进制字符串
   * @return byte[]
   */
  private static byte[] fromHex(String hex) {
    byte[] binary = new byte[hex.length() / 2];
    for (int i = 0; i < binary.length; i++) {
      binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }
    return binary;
  }

  /**
   * 将一个byte[]转成一个十六进制字符串
   *
   * @param array 需要转换的byte[]
   * @return 一个长度是要转换byte[]长度两倍的字符串
   */
  private static String toHex(byte[] array) {
    BigInteger bi = new BigInteger(1, array);
    // 讲数字转换成一个16进制字符串
    String hex = bi.toString(16);
    // 判断十六进制字符串长度是否够长，不足则补0
    int paddingLength = (array.length * 2) - hex.length();
    if (paddingLength > 0) {
      return String.format("%0" + paddingLength + "d", 0) + hex;
    } else {
      return hex;
    }
  }

  /**
   * Tests the basic functionality of the PasswordHash class
   *
   * @param args ignored
   */
  public static void main(String[] args) {
    try {
      // Print out 10 hashes
      for (int i = 0; i < 10; i++) {
        System.out.println(PasswordHash.createHash("p\r\nassw0Rd!"));
      }

      // Test password validation
      boolean failure = false;
      System.out.println("Running tests...");
      for (int i = 0; i < 100; i++) {
        String password = "" + i;
        String hash = createHash(password);
        String secondHash = createHash(password);
        if (hash.equals(secondHash)) {
          System.out.println("FAILURE: TWO HASHES ARE EQUAL!");
          failure = true;
        }
        String wrongPassword = "" + (i + 1);
        if (validatePassword(wrongPassword, hash)) {
          System.out.println("FAILURE: WRONG PASSWORD ACCEPTED!");
          failure = true;
        }
        if (!validatePassword(password, hash)) {
          System.out.println("FAILURE: GOOD PASSWORD NOT ACCEPTED!");
          failure = true;
        }
      }
      if (failure) {
        System.out.println("TESTS FAILED!");
      } else {
        System.out.println("TESTS PASSED!");
      }
    } catch (Exception ex) {
      System.out.println("ERROR: " + ex);
    }
  }
}
