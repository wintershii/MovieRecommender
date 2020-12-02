# from  import crawl
from bs4 import BeautifulSoup
import re
import time
from collections import deque
import requests
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
        # r.encoding = r.apparent_encoding
        r.encoding = 'utf-8'
        return r.text
    except:
        print("Failed!")


def getData(html):
    soup = BeautifulSoup(html, "html.parser")
    movieDetail = soup.find('div',
                            attrs={'id':
                                   'content'})  # 找到第一个class属性值为grid_view的ol标签
    article = movieDetail.find('div', attrs={'class': 'article'})
    data = []
    director = article.find('span', attrs={
        'class': 'attrs'
    }).find('a').getText()
    data.append(director)
    actors = article.find('span', attrs={
        'class': 'actor'
    })
    actorStr = ''
    if (actors):
        actors = actors.find('span', attrs={'class': 'attrs'})
        limit = 3
        cur = 0
        for actor in actors.find_all('a'):
            if (cur >= limit):
                break
            actorStr += actor.getText()
            actorStr += '/'
            cur = cur + 1
        data.append(actorStr)
    else:
        actorStr = '无'
        data.append(actorStr)
    plotAll = article.find('div', attrs={'class': 'related-info'})
    plot = plotAll.find('span', attrs={'class': 'all hidden'})
    if (plot):
        data.append(plot.getText())
    else:
        plot = plotAll.find('span', attrs={'property': 'v:summary'})
        data.append(plot.getText())
    commentAll = article.find('div', attrs={
        'id': 'comments-section'
    }).find('div', attrs={'id': 'hot-comments'})
    # print(commentAll)
    comments = ''
    for comment in commentAll.find_all('div', attrs={'class': 'comment-item'}):
        # print(comment.find('div', attrs={'class': 'comment'}).find('p', attrs={'comment-content'}))
        cm = comment.find('div', attrs={
            'class': 'comment'
        })
        if (cm):
            comments += cm.find('p', attrs={'comment-content'}).find('span', attrs={'class': 'short'}).getText()
        else:
            comments += ''
        comments += '\t'
    data.append(comments)
    print(director)
    print(actorStr)
    print(plot.getText())
    print(comments)
    print(data)
    sql = "INSERT INTO movie_details(m_id, director, actor, plot, comments) VALUES(%s, %s, %s, %s, %s)"
    data = (m_id, data[0], data[1], data[2], data[3])
    result = writeDb(sql, data)
    if (not result):
        print('写入失败')


def addUrl(html):
    soup = BeautifulSoup(html, "html.parser")
    movieList = soup.find('ol',
                          attrs={'class':
                                 'grid_view'})  # 找到第一个class属性值为grid_view的ol标签
    for movieLi in movieList.find_all('li'):  # 找到所有li标签
        movieDetails = movieLi.find('div', attrs={'class': 'hd'})
        movieDetailsUrl = movieDetails.find('a')
        if (movieDetailsUrl):
            # print(movieDetailsUrl)
            q.append(movieDetailsUrl['href'])


basicUrl = 'https://movie.douban.com/top250'
q = deque()
m_id = 2
k = 0
while k <= 225:
    html = getHTMLText(basicUrl, k)
    time.sleep(2)
    k += 25
    addUrl(html)

print(q)
print('爬取详情页url成功，开始爬取详情页数据')

for url in q:
    k = 0
    while k < 25:
        detailsHtml = getHTMLText(url, k)
        time.sleep(2)
        k += 25
        getData(detailsHtml)
    m_id = m_id + 1

# testUrl = 'https://movie.douban.com/subject/1292052/'
# k = 0
# while k < 25:
#     html = getHTMLText(testUrl, k)
#     time.sleep(2)
#     k += 25
#     getData(html)