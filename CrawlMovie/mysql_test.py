# import MySQLdb

import pymysql

db_name = 'movies'
db_user = 'root'
db_pass = '990708'
db_ip = '127.0.0.1'
db_port = 3306


# 写入数据到数据库中
def writeDb(sql, db_data=()):
    """
    连接mysql数据库（写），并进行写的操作
    """
    try:
        conn = pymysql.connect(db=db_name,
                               user=db_user,
                               passwd=db_pass,
                               host=db_ip,
                               port=int(db_port),
                               charset="utf8")
        cursor = conn.cursor()
    except Exception as e:
        print(e)
        print('数据库连接失败:%s' % e)
        return False

    try:
        cursor.execute(sql, db_data)
        conn.commit()
    except Exception as e:
        conn.rollback()
        print('数据写入失败:%s' % e)
        return False
    finally:
        cursor.close()
        conn.close()
    return True


sql = "INSERT INTO movie_description(name, score, peoples, describ, pic) VALUES(%s, %s, %s, %s, %s)"
data = ('111', '222', '333', '444', '555')
result = writeDb(sql, data)