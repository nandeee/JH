'use strict';

angular.module('jhipsterApp')
    .controller('Test8Controller', function ($scope, Test8) {
        window.scope = $scope;
        $scope.test8s = [];
        $scope.loadAll = function() {
            Test8.get(function(result) {
               $scope.test8s = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Test8.get({id: id}, function(result) {
                $scope.test8 = result;
            });
        };

        $scope.save = function () {
            if ($scope.test8.id != null) {
                Test8.update($scope.test8,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Test8.save($scope.test8,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.edit = function (entity) {
            $scope.test8 = entity;
            $scope.showForm = true;
        };

        $scope.delete = function (id) {
            Test8.delete({id: id},
                function () {
                    $scope.loadAll();
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.test8 = {name: null, age: null, date: null, time: null, id: null};

        $scope.clear = function () {
            $scope.test8 = {name: null, age: null, date: null, time: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

        };

        $scope.gridOptions = {
            data: 'test8s',
            enableFiltering: true,
            onRegisterApi: function(gridApi) {
                $scope.gridApi = gridApi;
                gridApi.edit.on.afterCellEdit($scope, function(rowEntity, colDef, newValue, oldValue) {
                    Test8.update(rowEntity,
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
                field: "name",
                displayName: "name",
                editableCellTemplate: '<div> <form name="inputForm"> <input type="text" required minlength=5 maxlength=10 ng-class="\'colt\' + col.uid" ui-grid-editor ng-model="MODEL_COL_FIELD" /> </form></div>'

            }, {
                field: "age",
                displayName: "age",
                editableCellTemplate: '<div> <form name="inputForm"> <input type="number" required min=2 max=10 ng-class="\'colt\' + col.uid" ui-grid-editor ng-model="MODEL_COL_FIELD" /> </form></div>'

            }, {
                field: "date",
                displayName: "date",
                editableCellTemplate: '<div> <form name="inputForm"> <input type="date" ng-class="\'colt\' + col.uid" ui-grid-editor ng-model="MODEL_COL_FIELD" /> </form></div>'

            }, {
                field: "time",
                displayName: "time",
                editableCellTemplate: '<div> <form name="inputForm"> <input type="datetime-local" ng-class="\'colt\' + col.uid" ui-grid-editor ng-model="MODEL_COL_FIELD" /> </form></div>'


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
