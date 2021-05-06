# cljmap-plantuml

Generate diagram from clojure map(or seq) using PlantUML json.

## Dependencies

Cljmap-plantuml uses graphviz for generating image. Please refer to <https://graphviz.org/download/>. 

## Installation

Installation is not ready yet.

## Usage

Define clojure map and simply call generation function.

```clojure
(ns my-test
    (:require [cljmap-view :refer [cljmap->image]])))

(def staff {:company "abc"
            :employees [{:name "kim" :phone "001-0000-0000"}
                        {:name "lala" :phone "002-0000-0000"}]})


(cljmap->image staff {:path "staff-graph.png"})
                     

```

Generated file "staff-graph.png" is belows :

![](https://github.com/jaeyeon-jo-kr/cljmap-plantuml/blob/main/staff-graph.png)

To adding highlight effect to map graph, set option like belows : 

```clojure
(cljmap->image
   {:firstName "John" :lastName "Smith"
    :address {:city "New York"
              :state "NY}
    :phoneNumbers [{:type "home" :number "212 555-1234"}]}
    {:path "abcd.png"
     :highlight [[:lastName]
                 [:address :city]
                 [:phoneNumbers 0 :number]]})
```
The result is :
![](https://github.com/jaeyeon-jo-kr/cljmap-plantuml/blob/main/staff-graph.png)

