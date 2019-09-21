import pandas as pd
import numpy as np

df = pd.read_csv('result.csv')
df['tag_list'] = df.apply(lambda x : x['tags'].split("|"), axis=1)
bucketList = ['adventure-with-kids', 'recreation-without-nature', 'education' ]
places = []
for bucket in bucketList:
    for i in range(len(df)):
        if bucket in df['tag_list'].iloc[i]:
            places.append(df['name'].iloc[i])
print(places)
places = np.array(places)
print(np.unique(places))
