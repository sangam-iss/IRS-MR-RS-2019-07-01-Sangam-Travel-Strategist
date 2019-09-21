; Start CLIPS

;Define characters to be used

(deftemplate traveller (slot travellerType (type SYMBOL)(allowed-symbols solo couple family friends)))
(deftemplate kids (slot hasKids (type SYMBOL)(allowed-symbols yes no) (default no)))
(deftemplate days (slot noOfDays (type INTEGER)))
(deftemplate firstTime (slot isFirstTime (type SYMBOL)(allowed-symbols yes no) ))
(deftemplate sightseeing (slot likeSightSeeing (type SYMBOL)(allowed-symbols yes no) ))
(deftemplate nature (slot likeNature (type SYMBOL)(allowed-symbols yes no) ))
(deftemplate adventure (slot likeAdventure (type SYMBOL)(allowed-symbols yes no) ))
(deftemplate recreation (slot likeRecreation (type SYMBOL)(allowed-symbols yes no) ))
(deftemplate arts (slot likeArts (type SYMBOL)(allowed-symbols yes no) ))
(deftemplate education (slot likeEducation (type SYMBOL)(allowed-symbols yes no) ))
(deftemplate shopping (slot likeShopping (type SYMBOL)(allowed-symbols yes no) ))
(deftemplate fun (slot likeFun (type SYMBOL)(allowed-symbols yes no) ))

;Define rules to be followed

(defrule firstTimer
    (firstTime (isFirstTime yes))
    =>
    (assert (sightseeing (likeSightSeeing yes)))
    )

(defrule append
?append <- (append ?who)
?old-list <- (qlist $?front)
=>
(retract ?append ?old-list)
(assert (qlist ?front ?who))
)

(defrule addToBucket
?addToBucket <- (addToBucket ?who)
?old-list <- (blist $?front)
=>
(retract ?addToBucket ?old-list)
(assert (blist ?front ?who))
)

(defrule welcome
=>
(printout t "Welcome. " crlf crlf)
(assert (append traveller))
(assert (append firstTime))
(assert (append days)))

(defrule check-kids
(traveller (travellerType family))
=>
(assert (append kids)))


(defrule check-firsttime
(firstTime (isFirstTime no))
=>
(assert (append sightseeing)))

(defrule couple
(traveller (travellerType couple))
=>
(assert (append shopping))
(assert (append recreation))
(assert (append adventure))
(assert (append nature))
)

(defrule friends
(traveller (travellerType friends))
=>
(assert (append fun))
(assert (append recreation))
(assert (append adventure))
(assert (append nature))
)

(defrule family
(traveller (travellerType family))
(kids (hasKids no))
=>
(assert (append nature))
(assert (append recreation))
(assert (append arts))
(assert (append shopping))
)

(defrule familyWithKids
(traveller (travellerType family))
(kids (hasKids yes))
=>
(assert (append nature))
(assert (append recreation))
(assert (append education))
(assert (append shopping))
(assert (append adventure))
)

(defrule solo
(traveller (travellerType solo))
=>
(assert (append nature))
(assert (append recreation))
(assert (append adventure))
(assert (append fun))
)

(defrule arts
(arts (likeArts yes))
=>
(assert (addToBucket arts))
)

(defrule education
(education (likeEducation yes))
=>
(assert (addToBucket education))
)

(defrule fun
(fun (likeFun yes))
=>
(assert (addToBucket fun))
)

(defrule shopping
(shopping (likeShopping yes))
=>
(assert (addToBucket shopping))
)

(defrule sightseeing
(sightseeing (likeSightSeeing yes))
=>
(assert (addToBucket sightseeing))
)

(defrule nature
(nature (likeNature yes))
=>
(assert (addToBucket nature))
)

(defrule nature-adventure
(nature (likeNature yes))
(adventure (likeAdventure yes))
(kids (hasKids no))
=>
(assert (addToBucket nature-adventure))
)

(defrule adventureWithoutKids
(adventure (likeAdventure yes))
(kids (hasKids no))
=>
(assert (addToBucket adventure-without-kids))
)

(defrule adventureWithKids
(adventure (likeAdventure yes))
(kids (hasKids yes))
=>
(assert (addToBucket adventure-with-kids))
)

(defrule recreationWithNature
(nature (likeNature yes))
(recreation (likeRecreation yes))
=>
(assert (addToBucket recreation-with-nature))
)

(defrule recreationWithoutNature
(nature (likeNature no))
(recreation (likeRecreation yes))
=>
(assert (addToBucket recreation-without-nature))
)