(ns cljmap-view
  (:require [clojure.data.json :as json])
  (:import (java.io FileOutputStream)
           (net.sourceforge.plantuml SourceStringReader)))

(defn ->str
  [key]
  (cond
    (keyword? key) (name key)
    (int? key) (str key)))

(defn highlight-str
  [keys]
  (->> keys
       (map (comp #(str "\"" % "\"") ->str))
       (interpose " / ")
       (apply str "#highlight ")))

(defn add-pre-highlight
  [json-str {:keys [highlight] :as opt}]
  (-> (->> highlight
           (map highlight-str)
           (interpose "\n")
           (apply str))
      (str "\n" json-str "\n")))

(defn add-pre-start
  [json-str]
  (str "@startjson\n" json-str))

(defn add-post-end
  [json-str]
  (str json-str "\n@endjson\n"))


(defn- json-uml-str
  "Translate clojure map to plantuml json file."
  [clj-map opt]
  (-> (json/write-str clj-map)
      (add-pre-highlight opt)
      add-pre-start
      add-post-end))

(defn- create-image!
  "Create image from plantuml string."
  [uml output-file]
  (with-open [out (clojure.java.io/output-stream output-file)]
    (-> (SourceStringReader. uml)
        (.outputImage out))))

(defn cljmap->image
  "Generate image from clojure data structure such as map and sequence."
  ([cljmap]
   (cljmap->image
    cljmap
    {:path (str (java.util.UUID/randomUUID) ".png")}))
  ([cljmap {:keys [path] :as opt}]
   (-> cljmap
       (json-uml-str opt)
       (create-image! path))))

  

(comment
  (cljmap->image
   {:firstName "John" :lastName "Smith"
    :address {:city "New York"
              :state "NY}
    :phoneNumbers [{:type "home" :number "212 555-1234"}]}
    {:path "abcd.png"
     :highlight [[:lastName]
                 [:address :city]
                 [:phoneNumbers 0 :number]]}))
