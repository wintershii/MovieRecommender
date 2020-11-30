import requests
import pymysql

db_name = 'movies'
db_user = 'root'
db_pass = '990708'
db_ip = '127.0.0.1'
db_port = 3306


def download_img(img_url):
    print(img_url)
    # header = {}
    list = img_url.split('/')
    str = list[len(list) - 1]
    print(str)
    r = requests.get(img_url)
    print(r.status_code)
    open('E:\\images\\' + str, 'wb').write(r.content)
    del r


def get_urls(sql):
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
        cursor.execute(sql)
        conn.commit()
        result = cursor.fetchall()
    except Exception as e:
        conn.rollback()
        print('数据写入失败:%s' % e)
        return False
    finally:
        cursor.close()
        conn.close()
    return result


# img_url = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p2372307693.jpg"
# download_img(img_url)

sql = "SELECT pic FROM movie_description"
img_urls = get_urls(sql)
# print(img_urls)

for url in img_urls:
    download_img(url[0])