(ns motunui.Main
  (:gen-class)
  (:import
    [java.sql Date]
    [java.time LocalDate]
    [ranoraraku.beans StockPriceBean]
    [ranoraraku.models.mybatis StockMapper]
    [vega.filters.ehlers
       Itrend
       CyberCycle
       SuperSmoother
       RoofingFilter])
  (:require
    [tongariki.common :as COM]
    [tongariki.db :as DB]))

;;;------------------------------------------------------------------------
;;;-------------------------- gen-class methods ---------------------------
;;;------------------------------------------------------------------------
;(defn -init []
;  [[] (atom {})])

(def calc-cc-10 (comp (CyberCycle. 10) (SuperSmoother. 10)))

(def calc-cc-10r (comp (CyberCycle. 10) (RoofingFilter.)))

(def min-dx (LocalDate/of 2014 1 1))

(def min-sql-date (Date/valueOf ^LocalDate min-dx))

(defn prices [oid]
  (DB/with-session "mybatis.conf.xml" StockMapper
    (.selectStockPrices ^StockMapper it oid min-sql-date)))

;(defn write-file []
;  (with-open [w (clojure.java.io/writer  "../R/w.txt" :append true)]
;    (.write w (str "hello" "world"))))

(def normalize-dates (partial COM/normalize-dates min-dx)) 

(defn to-R [oid prices num-items]
  (with-open [w (clojure.java.io/writer  (str "../R/" oid ".txt"))]
    (let [
          spots (map #(.getCls ^StockPriceBean %) prices)
          num-drop (- (.size spots) num-items)
          dx (map #(.toLocalDate ^Date (.getDx ^StockPriceBean %)) prices)
          cc (COM/normalize (drop num-drop (calc-cc-10 spots)))
          ccr (COM/normalize (drop num-drop (calc-cc-10r spots)))
          ndx (normalize-dates (drop num-drop dx))]
      (.write w (str "n\tcc\tccr\n"))
      (COM/process-lists-with #(.write w (str %1 "\t" %2 "\t" %3 "\n")) ndx cc ccr))))

(defn -main [& args]
  (let [oid (Integer/parseInt (first args))
        ni (Integer/parseInt (first (rest args)))
        px (prices oid)]
    (to-R oid px ni)))
