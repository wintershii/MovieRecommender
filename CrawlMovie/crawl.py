import requests
from bs4 import BeautifulSoup
import re
import time
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


def getHTMLText(url, k):
    try:
        if (k == 0):
            kw = {}
        else:
            kw = {'start': k, 'filter': ''}
        r = requests.get(url, params=kw, headers={'User-Agent': 'Mozilla/4.0'})
        r.raise_for_status()
        r.encoding = r.apparent_encoding
        return r.text
    except:
        print("Failed!")


def getData(html):
    soup = BeautifulSoup(html, "html.parser")
    movieList = soup.find('ol',
                          attrs={'class':
                                 'grid_view'})  # 找到第一个class属性值为grid_view的ol标签
    for movieLi in movieList.find_all('li'):  # 找到所有li标签
        data = []
        # 得到电影名字
        movieHd = movieLi.find('div', attrs={'class':
                                             'hd'})  # 找到第一个class属性值为hd的div标签
        movieName = movieHd.find('span', attrs={
            'class': 'title'
        }).getText()  # 找到第一个class属性值为title的span标签
        # 也可使用.string方法
        data.append(movieName)

        # 得到电影的评分
        movieScore = movieLi.find('span', attrs={
            'class': 'rating_num'
        }).getText()
        data.append(movieScore)

        # 得到电影的评价人数
        movieEval = movieLi.find('div', attrs={'class': 'star'})
        movieEvalNum = re.findall(r'\d+', str(movieEval))[-1]
        data.append(movieEvalNum)

        # 得到电影的短评
        movieQuote = movieLi.find('span', attrs={'class': 'inq'})
        if (movieQuote):
            data.append(movieQuote.getText())
        else:
            data.append("无")

        # 得到电影图片
        moviePic = movieLi.find('div', attrs={'class': 'pic'})
        moviePicUrl = moviePic.find('img')
        if (moviePicUrl):
            urlParts = moviePicUrl['src'].split('/')
            data.append(urlParts[len(urlParts)-1])
        else:
            data.append("无")

        print(data)
        sql = "INSERT INTO movie_description(name, score, peoples, describ, pic) VALUES(%s, %s, %s, %s, %s)"
        data = (data[0], data[1], data[2], data[3], data[4])
        result = writeDb(sql, data)
        if (not result):
            print('写入失败')


basicUrl = 'https://movie.douban.com/top250'
k = 0
while k <= 225:
    html = getHTMLText(basicUrl, k)
    time.sleep(2)
    k += 25
    getData(html)
