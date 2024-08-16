
approute.controller("profile-ctrl", function ($scope, $http) {
    $scope.items = []
    $scope.form = {};
    $scope.authorities = [];


    $scope.initialize = function () {
        $http.get("/rest/profile").then(resp => {
            $scope.items = resp.data;
            $scope.items.forEach(item => {
                $scope.loadAuthorities(item.username);
            });
        }).catch(error => {
            alert("Lỗi tải dữ liệu sản phẩm");
            console.log("error", error);
        });
    }
    $scope.loadAuthorities = function (username) {
        console.log("Account:", username);
        if (!username) {
            console.error("Account is not defined");
            return $q.reject("Account is not defined"); // Sử dụng $q.reject để trả về promise bị từ chối
        }
        return $http.post("/rest/authorities/account", username).then(resp => {
            console.log("Authorities:", resp.data);
            // Tìm item trong $scope.items và gán authorities
            let item = $scope.items.find(i => i.username === username);
            if (item) {
                item.authorities = resp.data;
            }
        }).catch(error => {
            console.log("Error loading authorities:", error);
            return $q.reject(error); // Sử dụng $q.reject để trả về promise bị từ chối
        });
    };




    $scope.reset = function () {
        $scope.form = {};
    }
    $scope.edit = function (item) {
        $scope.form = angular.copy(item);
        $("#nav-home-tab").tab('show');
    }


    $scope.create = function () {
        var item = angular.copy($scope.form);

        $http.post('/rest/profile', item).then(resp => {
            $scope.items.push(resp.data);
            $scope.reset();
            alert("thêm tài khoản mới thành công")
        }).catch(error => {
            alert("lõi thêm mới tài khoản")
            console.log("Error", error);
        })
    }
    $scope.update = function () {
        var item = angular.copy($scope.form);
        $http.put(`/rest/profile/${item.username}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.username == item.username);
            if (index !== -1) {
                $scope.items[index] = resp.data;
            }
            $scope.reset();
            alert("Cập nhật thành công");
            $scope.items.forEach(item => {
                $scope.loadAuthorities(item.username);
            });
        }).catch(error => {
            alert("Lỗi cập nhật tài khoản");
            console.log("error", error);
        });
    }

    $scope.delete = function (item) {

        $http.delete(`/rest/profile/${item.username}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.username == item.username);
            $scope.items.splice(index, 1);
            $scope.reset();
            alert("Xóa thành công")
        }).catch(error => {
            alert("Lỗi cập nhật tài khoản");
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