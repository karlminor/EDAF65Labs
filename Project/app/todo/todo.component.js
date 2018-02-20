

(function(app) {
  app.TodoComponent = TodoComponent;
  TodoComponent.annotations = [
    new ng.core.Component({
      selector: 'todo',
      templateUrl: 'app/todo/todo.component.html',
    })
  ];

  TodoComponent.parameters = [ ng.router.ActivatedRoute, app.DataService ];

  function TodoComponent(route, dataService) {
   	this.todo = {text: "", done: false};
   	this.dataService = dataService;
  	 route.params.subscribe((params) => {
    		  tmp = dataService.getTODOs()[params['id']];
    		  if(tmp){ this.todo = tmp; }
  	 });
  }

  TodoComponent.prototype.newTodo = function(){

	this.dataService.addTodo({text: this.todo.text, done: this.todo.done});
  }

})(window.app = window.app || {}); 
