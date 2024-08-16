approute.controller("product-ctrl", function ($scope, $http) {
    $scope.items = []
    $scope.form = {};
    $scope.categories = []
    $scope.initialize = function () {
        $http.get("/rest/products").then(resp => {
            $scope.items = resp.data;
            $scope.items.forEach(item => {
                item.enteredDate = new Date(item.enteredDate);
            });
        }).catch(error => {
            alert("Lỗi tải dữ liệu sản phẩm");
            console.log("error", error);
        });
    }


    $scope.reset = function () {
        $scope.form = {
            enteredDate: new Date(),
            image: '1ab58c72.jpg'
        };
    }
    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $("#nav-home-tab").tab('show');
    }
    $http.get('/rest/products/categories').then(function (response) {
        $scope.categories = response.data;
    });

    $scope.statuses = ["AVAILABLE", "OUTOFORDER", " UNAVAILABLE"];
    $scope.create = function () {
        var item = angular.copy($scope.form);
        item.category = { categoryId: $scope.form.categoryId };
        $http.post('/rest/products', item).then(resp => {
            resp.data.enteredDate = new Date(resp.data.enteredDate)
            $scope.items.push(resp.data);
            $scope.reset();
            alert("thêm sản phẩm mới thành công")
        }).catch(error => {
            alert("lõi thêm mới sản phẩm")
            console.log("Error", error);
        })
    }
    $scope.update = function () {
        var item = angular.copy($scope.form);
        item.category = { categoryId: $scope.form.categoryId };
        $http.put(`/rest/products/${item.productID}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.productID == item.productID);
            if (index !== -1) {
                $scope.items[index] = resp.data;
            }
            $scope.reset();
            alert("Cập nhật thành công");
        }).catch(error => {
            alert("Lỗi cập nhật sản phẩm");
            console.log("error", error);
        });
    }

    $scope.delete = function (item) {

        $http.delete(`/rest/products/${item.productID}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.productID == item.productID);
            $scope.items.splice(index, 1);
            $scope.reset();
            alert("Xóa thành công")
        }).catch(error => {
            alert("Lỗi cập nhật sản phẩm");
            console.log("error", error);
        })
    }
    $scope.imageChanged = function (files) {
        var data = new FormData();
        data.append('file', files[0]);
        $http.post('/rest/upload/images', data, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        }).then(resp => {
            $scope.form.image = resp.data.name;

        }).catch(error => {
            alert("Lỗi load hình ảnh: " + (error.data && error.data.message ? error.data.message : 'Unknown error'));
            console.log("error", error);
        });
    }
    $scope.initialize();
    $scope.pager = {
        page: 0,
        size: 5,
        get items() {
            var start = this.page * this.size;
            var end = start + this.size;
            return $scope.items.slice(start, end);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.length / this.size);
        },
        first() {
            this.page = 0;
        },
        prev() {
            if (this.page > 0) {
                this.page--;
            }
        },
        next() {
            if (this.page < this.count - 1) {
                this.page++;
            }
        },
        last() {
            this.page = this.count - 1;
        }
    }

});