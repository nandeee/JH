'use strict';

angular.module('jhipsterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('courier', {
                parent: 'entity',
                url: '/couriers',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.courier.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courier/couriers.html',
                        controller: 'CourierController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courier');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('courier.detail', {
                parent: 'entity',
                url: '/courier/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'jhipsterApp.courier.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/courier/courier-detail.html',
                        controller: 'CourierDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('courier');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Courier', function($stateParams, Courier) {
                        return Courier.get({id : $stateParams.id});
                    }]
                }
            })
            .state('courier.new', {
                parent: 'courier',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courier/courier-dialog.html',
                        controller: 'CourierDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, daily_capacity: null, color_code: null, is_enabled: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('courier', null, { reload: true });
                    }, function() {
                        $state.go('courier');
                    })
                }]
            })
            .state('courier.edit', {
                parent: 'courier',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/courier/courier-dialog.html',
                        controller: 'CourierDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Courier', function(Courier) {
                                return Courier.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('courier', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
