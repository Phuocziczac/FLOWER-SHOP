var approute = angular.module("admin-app", ["ngRoute"]);
//approute á nha
approute.config(function ($routeProvider) {
    $routeProvider
        .when("/product", {
            templateUrl: "/admin/product/index.html",
            controller: "product-ctrl"
        })
        .when("/authority", {
            templateUrl: "/admin/Authority/index.html",
            controller: "authority-ctrl"
        })
        .when("/category", {
            templateUrl: "/admin/categories/list.html",
            controller: "category-ctrl"
        })
        .when("/profile", {
            templateUrl: "/admin/profile/list.html",
            controller: "profile-ctrl"
        })
        .when("/report", {
            templateUrl: "/admin/report.html",
            controller: "report-ctrl"
        })
});