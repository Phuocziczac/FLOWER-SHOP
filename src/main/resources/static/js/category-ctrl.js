
approute.controller("category-ctrl", function ($scope, $http) {
    $scope.items = []
    $scope.form = {};

    $scope.initialize = function () {
        $http.get("/rest/category").then(resp => {
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
        $scope.form = {};
    }
    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $("#nav-home-tab").tab('show');
    }


    $scope.create = function () {
        var item = angular.copy($scope.form);

        $http.post('/rest/category', item).then(resp => {
            $scope.items.push(resp.data);
            $scope.reset();
            alert("thêm danh mục mới thành công")
        }).catch(error => {
            alert("lõi thêm mới danh mục")
            console.log("Error", error);
        })
    }
    $scope.update = function () {
        var item = angular.copy($scope.form);
        $http.put(`/rest/category/${item.categoryId}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.categoryId == item.categoryId);
            if (index !== -1) {
                $scope.items[index] = resp.data;
            }
            $scope.reset();
            alert("Cập nhật thành công");
        }).catch(error => {
            alert("Lỗi cập nhật danh mục");
            console.log("error", error);
        });
    }

    $scope.delete = function (item) {

        $http.delete(`/rest/category/${item.categoryId}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.categoryId == item.categoryId);
            $scope.items.splice(index, 1);
            $scope.reset();
            alert("Xóa thành công")
        }).catch(error => {
            alert("Lỗi cập nhật danh mục");
            console.log("error", error);
        })
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