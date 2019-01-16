import { Component, OnInit } from '@angular/core';
import {AuthService} from "../../services/auth.service";
import {GlobalService} from "../../shared/services/global.service";
import {MenuTypeModel} from "../../model/menu-type.model";
import {MenuCategoryModel} from "../../model/menu-category.model";
import {DeliveryMenuService} from "../../services/delivery-menu.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import swal from 'sweetalert2';
import {Router} from "@angular/router";

@Component({
  selector: 'app-delivery-menu',
  templateUrl: './delivery-menu.component.html',
  styleUrls: ['./delivery-menu.component.scss']
})
export class DeliveryMenuComponent implements OnInit {
  loading: boolean = true;
  typeForm: FormGroup;
  categoryForm: FormGroup;
  menuTypes: MenuTypeModel[];
  menuCategories: MenuCategoryModel[];

  constructor(private router: Router,private _authService: AuthService, private _globalService: GlobalService,
              private deliveryMenuService: DeliveryMenuService, private formBuilder: FormBuilder) {
    this._authService.isAuthenticated();
    this.loading = false;
    this._globalService.dataBusChanged('pageLoading', false);
  }

  ngOnInit() {
    this._globalService.dataBusChanged('headerTitle', 'Меню');
    this.typeForm = this.formBuilder.group({
      id: [-1],
      displayName: ['', Validators.required],
      name: ['', Validators.required]
    });
    this.categoryForm = this.formBuilder.group({
      id: [-1],
      displayName: ['', Validators.required],
      name: ['', Validators.required]
    });
    this.loadDeliveryMenu();
  }

  selectMenuType( menuType: MenuTypeModel ){
    this.typeForm.get('id').setValue( menuType.id );
    this.typeForm.get('displayName').setValue( menuType.displayName );
    this.typeForm.get('name').setValue( menuType.name );
  }

  selectMenuCategory( menuCategory: MenuCategoryModel ){
    this.categoryForm.get('id').setValue( menuCategory.id );
    this.categoryForm.get('displayName').setValue( menuCategory.displayName );
    this.categoryForm.get('name').setValue( menuCategory.name );
  }

  clearType(){
    this.selectMenuType( new MenuTypeModel() );
  }

  clearCategory(){
    this.selectMenuCategory( new MenuCategoryModel() );
  }

  showMessage( data: any){
    if ( data.status === 200 ){
      swal('Обновление данных, успешно');
    } else {
      swal({
        type: 'error',
        title: data.status,
        text: data.message,
      });
    }
    this.loadDeliveryMenu();
  }

  saveType(){
    let currentType = new MenuTypeModel();
    currentType.id = this.typeForm.get('id').value;
    currentType.displayName = this.typeForm.get('displayName').value;
    currentType.name = this.typeForm.get('name').value;
    this._globalService.dataBusChanged('pageLoading', true);
    this.deliveryMenuService.saveMenuType( currentType ).subscribe( data => {
      this.showMessage( data );
    });
  }

  saveCategory(){
    let currentCategory = new MenuCategoryModel();
    currentCategory.id = this.categoryForm.get('id').value;
    currentCategory.displayName = this.categoryForm.get('displayName').value;
    currentCategory.name = this.categoryForm.get('name').value;
    this._globalService.dataBusChanged('pageLoading', true);
    this.deliveryMenuService.saveMenuCategory( currentCategory ).subscribe( data => {
      this.showMessage( data );
    });
  }

  deleteMenuType( id ){
    this.deliveryMenuService.deleteMenuType( id ).subscribe( data => {
      this.showMessage( data );
    });
  }

  deleteMenuCategory( id ){
    this.deliveryMenuService.deleteMenuCategory( id ).subscribe( data => {
      this.showMessage( data );
    });
  }

  deleteMenu(menu, type){
    swal({
      title: 'Подтверждение...',
      text: "Вы желаете удалить меню: '"+ menu.displayName+"'",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#16be9a',
      cancelButtonColor: '#ff7403',
      confirmButtonText: 'Да, удалить',
      cancelButtonText: 'Отмена'
    }).then((result) => {
      if (result.value) {
        if ( type == 'type' ){
          this.deleteMenuType( menu.id );
        } else if ( type == 'category' ){
          this.deleteMenuCategory( menu.id );
        }
      }
    })
  }

  getDeliveryMenu(){
    let deliveryMenu = this.deliveryMenuService.getDeliveryMenus();
    this.menuTypes = deliveryMenu.menuTypes;
    this.menuCategories = deliveryMenu.menuCategories;
  }

  loadDeliveryMenu(){
    this.deliveryMenuService.loadDeliveryMenus().subscribe( data => {
      this.menuTypes = data.menuTypes;
      this.menuCategories = data.menuCategories;
      this._globalService.dataBusChanged('pageLoading', false);
      this.loading = false;
    });
  }

  goBack(){
    this.router.navigate(['pages/company-edit']);
  }
}
