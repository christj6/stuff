(def hello "Hello Middle Earth")

(def dwarves (vector "Thorin" "Balin" "Bifur" "Bofur" "Bombur" "Dori" "Dwalin" "Fili" "Gloin" "Kili" "Nori" "Oin" "Ori"))

(def leader (first dwarves))

(def company (drop 1 dwarves))

(def full_company (conj dwarves "Bilbo"))

(def locations (vector {:name "The Shire" :temp 74} {:name "The Misty Mountains" :temp 12} {:name "Fangorn Forest" :temp 58} {:name "Mordor" :temp 117} {:name "Rivendell" :temp 71}))


(def warm_location_map (filter #(> (:temp %) 70) locations)) ; map of elements with temperature above 70 -- now need to parse it for the location strings

; (def warm_locations (map first warm_location_map)) ; almost worked, but not what I was looking for

(def warm_locations (map :name warm_location_map)) ; that works!
