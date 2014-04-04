(def fellowship [{:name "Frodo", :race "Hobbit"} {:name	"Sam", :race "Hobbit"} {:name "Pippin", :race "Hobbit"} {:name "Merry", :race "Hobbit"} {:name "Gandalf", :race "Wizard"} {:name "Aragorn", :race "Man"} {:name	"Boromir", :race "Man"}, {:name	
"Gimli", :race"Dwarf"}, {:name "Legolas", :race "Elf"}])

; included from company.clj
(def dwarves (vector "Thorin" "Balin" "Bifur" "Bofur" "Bombur" "Dori" "Dwalin" "Fili" "Gloin" "Kili" "Nori" "Oin" "Ori"))
(def company (drop 1 dwarves))

; (def lengths (count company)) ; this isn't what I'm looking for -- this gives the # of people in company. I want a list of the lengths of each name.

; #1
(def lengths (map count company)) ; list of the lengths of each name in company

; #2
(def sumOfLengths (reduce + lengths)) ; sums the lengths together

; #3
(def myList (filter #(> (count (:name %)) 5) fellowship)) ; extracts the members of fellowship with a name longer than 5 characters

; #4
(def longNames (map :name myList)) ; list of names longer than 5 characters in Fellowship

; #5
; (defn nameChange [{name :name race :race}] {:name "bob" :race race}) ; input {:name "Frodo", :race "Hobbit"}, output {:name "bob", :race "Hobbit"}
; (map nameChange fellowship) ; returns a new fellowship where everyone's name is "bob" -- on the right track.

;(defn makeStr [chars] (apply str chars)) ; call like: (makeStr ['a 'b 'c]) -- returns "abc"
; basically the same thing as: (apply str ['a 'b 'c])

; (defn parseString [string] (concat (take 1 string) (drop 1 string)))
(defn parseString [string] (into [] (concat (take 1 string) (drop 1 string)))) ; put in "bob", it returns [\b \o \b]
; (makeStr (parseString "bob")) returns "bob" -- awesome

(defn pig [chars] (concat (drop 1 chars) (take 1 chars) "-" "ay"))

(defn nameChange [{name :name race :race}] {:name (apply str (pig (parseString name))) :race race}) 
(def secretNames (map nameChange fellowship))







