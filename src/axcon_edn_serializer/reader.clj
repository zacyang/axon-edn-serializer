(ns axon.edn.serializer.reader
  (:require [clojure.edn :as edn]
            [miner.tagged :as tag]))

(defrecord AAA [a b c])
(defn read-string[str]

  )
(defn try-construct [tag val]
  (prn (type tag))
  (prn val)
  ;;if map map->
  ;;if list apply with ->
  (eval (read-string (str "(" (str tag) "." " \"a\" "  " \"b\")")))

  )

(defn ->record
  "tag need to be in the clojure tag format namespace/Type ,
in the prn-string it will form the symbol as namespace.Type"
  [tag]
  (when (and (symbol tag)
             (namespace tag))


    )
  )
