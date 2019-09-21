from django.shortcuts import render
from django.http import HttpResponse
import clips
import pandas as pd
import numpy as np
import json
import httplib2
questionLookup = {'traveller' : 
                        {'question':"What traveller type do you belong?",
                        'type': 'radio',
                        'options' : [{'value':'solo', 'name': 'Solo Traveller'},
                                        {'value':'family', 'name': 'Family'},
                                        {'value':'couple', 'name': 'Couple'},
                                        {'value':'friends', 'name': 'Friends'}],
                        'slot': 'travellerType' },
                        'kids' : 
                        {'question':"Do you have kids accompanying?",
                        'type': 'radio',
                        'options' : [{'value':'yes', 'name': 'Yes'}, {'value':'no', 'name': 'No'}],
                        'slot': 'hasKids' },
                        'days' : 
                        {'question':"What is the duration of your stay in days?",
                        'type': 'number',
                        'slot': 'noOfDays' },
                        'firstTime' : 
                        {'question':"Are you visiting Singapore for the first time?",
                        'type': 'radio',
                        'options' :  [{'value':'yes', 'name': 'Yes'}, {'value':'no', 'name': 'No'}],
                        'slot': 'isFirstTime' },
                    'sightseeing' : 
                        {'question':"Do you prefer sight seeing?",
                        'type': 'radio',
                        'options' :  [{'value':'yes', 'name': 'Yes'}, {'value':'no', 'name': 'No'}],
                        'slot': 'likeSightSeeing' },
                        'nature' : 
                        {'question':"Are you a nature lover?",
                        'type': 'radio',
                        'options' :  [{'value':'yes', 'name': 'Yes'}, {'value':'no', 'name': 'No'}],
                        'slot': 'likeNature' },
                        'adventure' : 
                        {'question':"Do you like adventure?",
                        'type': 'radio',
                        'options' :  [{'value':'yes', 'name': 'Yes'}, {'value':'no', 'name': 'No'}],
                        'slot': 'likeAdventure' },
                        'recreation' : 
                        {'question':"Do you prefer recreational activities?",
                        'type': 'radio',
                        'options' :  [{'value':'yes', 'name': 'Yes'}, {'value':'no', 'name': 'No'}],
                        'slot': 'likeRecreation' },
                        'arts' : 
                        {'question':"Do you like arts and cultural places?",
                        'type': 'radio',
                        'options' :  [{'value':'yes', 'name': 'Yes'}, {'value':'no', 'name': 'No'}],
                        'slot': 'likeArts' },
                        'education' : 
                        {'question':"Do you wish to visit educational places?",
                        'type': 'radio',
                        'options' :  [{'value':'yes', 'name': 'Yes'}, {'value':'no', 'name': 'No'}],
                        'slot': 'likeEducation' },
                        'shopping' : 
                        {'question':"Do you wish to do shopping?",
                        'type': 'radio',
                        'options' :  [{'value':'yes', 'name': 'Yes'}, {'value':'no', 'name': 'No'}],
                        'slot': 'likeShopping' },
                        'fun' : 
                        {'question':"Do you prefer fun and entertainment activities?",
                        'type': 'radio',
                        'options' :  [{'value':'yes', 'name': 'Yes'}, {'value':'no', 'name': 'No'}],
                        'slot': 'likeFun' } }
next_index = 2
waitFlag = True
lookupid = ''
env = clips.Environment()
questionList = []

# Create your views here.
def index(request):
    global next_index
    env.clear()
    next_index = 2
    return render(request, 'index.html')

def planner(request):
    global next_index, questionLookup, lookupid, env, questionList
    print('Entering planner...')
    

    env.load('tourPlanner/planner.clp')
    fact_string = "(qlist start)"
    fact = env.assert_string(fact_string)
    fact_string = "(blist start)"
    fact = env.assert_string(fact_string)

    
    env._agenda.run()
    factList =[]
    print('new facts...')
    for fact in env.facts():
        factList.append(fact)
    print(factList)
    for fact in factList:
        if 'qlist' in str(fact):
            string = str(fact)
    
    opening = string.find('(')
    string = string[opening:-1]
    questionList = string.split()
    print('the questions are')
    print(questionList)
    lookupid = questionList[next_index]

    if(next_index < len(questionList) ):
        question = questionLookup[lookupid]['question']
        inputType = questionLookup[lookupid]['type']
        return render(request,'question.html',context={'question': question, 'type': inputType})
    return None

def answer(request):
    global next_index, questionLookup, lookupid, env, questionList
    answer = request.POST.get("answer")
    print('the answer is', answer)
    template = env.find_template(lookupid)
    new_fact = template.new_fact()
    new_fact[questionLookup[lookupid]['slot']] = clips.Symbol(answer)
    new_fact.assertit()
    next_index = next_index +1
    for fact in env.facts():
        print(fact)
   
    
    if(next_index < len(questionList) ):
        lookupid = questionList[next_index]
        question = questionLookup[lookupid]['question']
        inputType = questionLookup[lookupid]['type']
        options = questionLookup[lookupid]['options']
        return render(request,'question.html',context={'question': question, 'type': inputType, 'options': options})
    
    env._agenda.run()
    factList =[]
    print('new facts...')
    for fact in env.facts():
        factList.append(fact)
    print(factList)
    for fact in factList:
        if 'qlist' in str(fact):
            string = str(fact)
    opening = string.find('(')
    string = string[opening:-1]
    questionList = string.split()
    print('the questions are')
    print(questionList)

    if(next_index < len(questionList) ):
        lookupid = questionList[next_index]
        question = questionLookup[lookupid]['question']
        inputType = questionLookup[lookupid]['type']
        options = questionLookup[lookupid]['options']
        return render(request,'question.html',context={'question': question, 'type': inputType, 'options': options})
    df = pd.read_csv('result.csv')
    df['tag_list'] = df.apply(lambda x : x['tags'].split("|"), axis=1)
    env._agenda.run()
    factList =[]
    print('new facts...')
    for fact in env.facts():
        factList.append(fact)
    print(factList)
    for fact in factList:
        if 'blist' in str(fact):
            string = str(fact)
    opening = string.find('(')
    string = string[opening:-1]
    bucketList = string.split()
    print('the buckets are')
    print(bucketList)
    places = []
    for bucket in bucketList:
        for i in range(len(df)):
            if bucket in df['tag_list'].iloc[i]:
                places.append(df['id'].iloc[i])
    places = np.unique(np.array(places))
    print(places)
    mask = df['id'].isin(places)
    df = df.loc[mask]
    locations = df.to_dict('records')
    for fact in factList:
        if 'noOfDays' in str(fact):
            string = str(fact)
    string = string[:-2]
    dayList = string.split()
    day = int(dayList[-1])
    print('no of days', day)
    json_data = {"days": day, "locations": locations}
    with open('test.json', 'w') as f:
        json.dump(json_data, f)
    try:
        http = httplib2.Http(timeout=40)
        plannerUrl = "http://127.0.0.1:8080/sangam/tour/planItinerary"
        headers = {'Content-type': 'application/json'}
        response,content=http.request(plannerUrl, 'POST',headers=headers,body=json.dumps(json_data))
        if content is None or content == "":
            return HttpResponse('Please choose some categories')
        json_response = json.loads(content)

        
        return render(request,'result.html',context={'list_result' : json_response})
    except:
        return render(request,'error.html')

def budget(request):
    with open('test.json', 'r') as f:
        json_data = json.load(f)
    http = httplib2.Http(timeout=40)
    plannerUrl = "http://127.0.0.1:8080/sangam/tour/planBudgetItinerary"
    headers = {'Content-type': 'application/json'}
    response,content=http.request(plannerUrl, 'POST',headers=headers,body=json.dumps(json_data))
    if content is None or content == "":
        return HttpResponse('Please choose some categories')
    json_response = json.loads(content)

    
    return render(request,'result.html',context={'list_result' : json_response, 'budget' : True})


