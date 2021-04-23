import numpy as np
from sklearn.model_selection import train_test_split

data = np.loadtxt('c:///Users/mirac/Desktop/MovieRecommender/ranker/real_train/part-00000-5c5b17d0-6e97-489a-aa88-1f15b8a11319.csv', dtype=int, delimiter=',', skiprows=1)
X, y = data[:, :-1], data[:, -1]
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)
train = np.column_stack((X_train, y_train))
np.savetxt('train_set.csv', train, delimiter=',', header='userId,movieId,label', fmt='%d', comments='')

test = np.column_stack((X_test, y_test))
np.savetxt('test_set.csv', test, delimiter=',', header='userId,movieId,label', fmt='%d', comments='')
