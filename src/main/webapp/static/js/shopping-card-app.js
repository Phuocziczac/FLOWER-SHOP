const app = angular.module("shopping-card-app", []);

app.controller("shopping-card-ctrl", function ($scope, $http) {
    $scope.cart = {
        items: [],

        saveToLocalStorage() {
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart", json);
        },
        add(id) {
            var item = this.items.find(item => item.productID == id);

            if (item) {

                item.qty++;
                this.saveToLocalStorage();
            }
            else {

                $http.get(`/rest/products/${id}`).then(resp => {
                    resp.data.qty = 1;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                });
            }
        },
        get count() {
            return this.items
                .map(item => item.qty)
                .reduce((total, qty) => total += qty, 0);
        },
        get amount() {
            return this.items
                .map(item => item.qty * item.unitPrice)
                .reduce((total, amount) => total += amount, 0);
        },
        loadFromLocalStorage() {
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json) : [];
        },
        remove(id) {
            var index = this.items.findIndex(item => item.productID == id);
            alert(index);
            this.items.splice(index, 1);
            this.saveToLocalStorage();
        },
        clear() {
            this.items = [];
            this.saveToLocalStorage();
        }
    };
    $scope.cart.loadFromLocalStorage();
});
