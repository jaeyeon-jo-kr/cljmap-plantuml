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


(cljmap->image staff "staff-graph.png")
                     

```

Generated file "staff-graph.png" is belows :

![](https://github.com/jaeyeon-jo-kr/cljmap-plantuml/blob/main/staff-graph.png)
