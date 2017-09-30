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

(def min-dx (LocalDate/of 2014 1 1))

(def min-sql-date (Date/valueOf ^LocalDate min-dx))

(defn prices [oid]
  (DB/with-session "mybatis.conf.xml" StockMapper
    (.selectStockPrices ^StockMapper it oid min-sql-date)))

(defn normalize-dates [dx]
  (map #(COM/diff-days min-dx %) dx))

(defn to-R [prices]
  (let [spots (map #(.getCls ^StockPriceBean %) prices)
        dx (map #(.toLocalDate ^Date (.getDx ^StockPriceBean %)) prices)
        cc (calc-cc-10 spots)
        ndx (normalize-dates dx)]
    (COM/process-lists-with println ndx cc)))

(defn -main [& args]
  (let [oid (Integer/parseInt (first args))
        px (prices oid)]
    (to-R px)))
