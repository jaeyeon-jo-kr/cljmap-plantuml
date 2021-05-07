(ns cljmap-view
  (:require [clojure.data.json :as json])
  (:import (java.io FileOutputStream)
           (net.sourceforge.plantuml SourceStringReader)))

(defn ->str
  [key]
  (cond
    (keyword? key) (name key)
    (int? key) (str key)))

(defn ->highlight
  [{:keys [highlight]}]
  highlight)

(defn ->path
  [{:keys [path]}]
  path)

(defn highlight-str
  [keys]
  (->> keys
       (map (comp #(str "\"" % "\"") ->str))
       (interpose " / ")
       (apply str "#highlight ")))

(defn add-pre-highlight
  [json-str highlight]
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

(defn- create-image!
  "Create image from plantuml string."
  [uml-str output-file]
  (with-open [out (clojure.java.io/output-stream output-file)]
    (-> (SourceStringReader. uml-str)
        (.outputImage out))))

(defn- json->uml-str
  "Translate clojure map to plantuml json file."
  [clj-map opt]
  (-> (json/write-str clj-map)
      (add-pre-highlight (->highlight opt))
      add-pre-start
      add-post-end))

(defn default-option []
  {:path (str (java.util.UUID/randomUUID) ".png")})

(defn cljmap->image
  "Generate image from clojure data structure such as map and sequence."
  ([cljmap]
   (cljmap->image cljmap (default-option)))
  ([cljmap opt]
   (-> cljmap
       (json->uml-str opt)
       (create-image! (->path opt)))))

  

(comment
  (cljmap->image
   {:firstName "John" :lastName "Smith"
    :address {:city "New York"
              :state "NY"}
    :phoneNumbers [{:type "home" :number "212 555-1234"}]}
    {:path "abcd.png"
     :highlight [[:lastName]
                 [:address :city]
                 [:phoneNumbers 0 :number]]}))
