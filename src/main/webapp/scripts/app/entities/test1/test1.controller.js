'use strict';

angular.module('jhipsterApp')
    .controller('Test1Controller', function ($scope, Test1) {
        window.scope = $scope;
        $scope.test1s = [];
        $scope.loadAll = function() {
            Test1.query(function(result) {
               $scope.test1s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test1.get({id: id}, function(result) {
                $scope.test1 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test1.id != null) {
                Test1.update($scope.test1,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test1.save($scope.test1,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.test1 = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Test1.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test1 = {firstname: null, age: null, date: null, time: null, flag: null, id: null};

        $scope.clear = function () {
            $scope.test1 = {firstname: null, age: null, date: null, time: null, flag: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

        };

        $scope.gridOptions = {
            data: 'test1s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test1.update(rowEntity,
                        function() {
                            $scope.refresh();
                        });
                });
            },
            columnDefs: [{
                field: "id",
                displayName: "ID",
                enableCellEdit: false,
                pinnedLeft: true

            }, {
                field: "firstname",
                displayName: "firstname"

            }, {
                field: "age",
                displayName: "age"

            }, {
                field: "date",
                displayName: "date"

            }, {
                field: "time",
                displayName: "time"

            }, {
                field: "flag",
                displayName: "flag"


            }, {
                name: 'placeholder',
                displayName: '',
                width: 150,
                enableSorting: false,
                enableCellEdit: false,
                enableFiltering: false,
                headerCellTemplate: '<span></span>',
                cellClass: 'tableButton',
                cellTemplate: '<div class="tableButton"><button id="delBtn" type="button" class="btn-small" ng-click="grid.appScope.delete(row.entity.id)">Delete</button><button id="editBtn" type="button" class="btn-small" ng-click="grid.appScope.edit(row.entity)">Edit</button></div>'
            }],
        };

        $scope.ctrl = {};
        $scope.ctrl.querySearch = querySearch;

        function querySearch(query, entity) {
            var results = query ? $scope[entity].filter(createFilterFor(query)) : $scope[entity];
            return results;
        }

        function createFilterFor(query) {
            var lowercaseQuery = angular.lowercase(query);
            return function filterFn(state) {
                return (state.name.toLowerCase().indexOf(lowercaseQuery) === 0);
            };
        }
    });
