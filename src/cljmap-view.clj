(ns cljmap-view
  (:require [clojure.data.json :as json])
  (:import (java.io FileOutputStream)
           (net.sourceforge.plantuml SourceStringReader)))


(defn- json-uml-str
  "Translate clojure map to plantuml json file."
  [clj-map]
  (str "@startjson\n"
       (json/write-str clj-map)
       "\n@endjson\n"))

(defn- create-image!
  "Create image from plantuml string."
  [uml output-file]
  (with-open [out (clojure.java.io/output-stream output-file)]
    (-> (SourceStringReader. uml)
        (.outputImage out))))

(defn cljmap->image
  "Generate image from clojure data structure."
  ([m]
   (cljmap->image m (str (java.util.UUID/randomUUID) ".png")))
  ([m path]
   (-> m json-uml-str (create-image! path))))

