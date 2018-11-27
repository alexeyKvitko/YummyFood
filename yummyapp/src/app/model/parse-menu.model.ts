export class ParseMenuModel {
  id: string;
  companyId: string;
  typeId: string;
  categoryId: string;
  htmlResponse: string;
  parseUrl: string;
  prefixUrl: string;
  tagTrash: string;
  tagEndSection: string;
  tagName: string;
  tagDescription: string;
  tagImageUrl: string;
  tagWeightOne: string;
  tagSizeOne: string;
  tagPriceOne: string;
  tagWeightTwo: string;
  tagSizeTwo: string;
  tagPriceTwo: string;
  tagWeightThree: string;
  tagSizeThree: string;
  tagPriceThree: string;
  tagWeightFour: string;
  tagSizeFour: string;
  tagPriceFour: string;
  processed: string;


  public getByName( name: string): string {
    debugger
    let value = null;
    switch ( name ) {
      case 'id': {
          value = this.id;
          break;}
      case 'companyId': { 
        value = this.companyId;
        break; }
      case 'typeId': { 
        value = this.typeId;
        break;}
      case 'categoryId': { 
        value = this.categoryId;
        break;}
      case 'htmlResponse': { 
        value = this.htmlResponse;
        break;}
      case 'parseUrl': { 
        value = this.parseUrl;
        break;}
      case 'prefixUrl': { 
        value = this.prefixUrl;
        break;}
      case 'tagTrash': { 
        value = this.tagTrash;
        break;}
      case 'tagEndSection': { 
        value = this.tagEndSection;
        break;}
      case 'tagName': { 
        value = this.tagName;
        break;}
      case 'tagDescription': { 
        value = this.tagDescription;
        break;}
      case 'tagImageUrl': { 
        value = this.tagImageUrl;
        break;}
      case 'tagWeightOne': { 
        value = this.tagWeightOne;
        break;}
      case 'tagSizeOne': { 
        value = this.tagSizeOne;
        break;}
      case 'tagPriceOne': { 
        value = this.tagPriceOne;
        break;}
      case 'tagWeightTwo': {
        value = this.tagWeightTwo;
        break;}
      case 'tagSizeTwo': {
        value = this.tagSizeTwo;
        break;}
      case 'tagPriceTwo': {
        value = this.tagPriceTwo;
        break;}
      case 'tagWeightThree': {
        value = this.tagWeightThree;
        break;}
      case 'tagSizeThree': {
        value = this.tagSizeThree;
        break;}
      case 'tagPriceThree': {
        value = this.tagPriceThree;
        break;}
      case 'tagWeightFour': {
        value = this.tagWeightFour;
        break;}
      case 'tagSizeFour': {
        value = this.tagSizeFour;
        break;}
      case 'tagPriceFour': {
        value = this.tagPriceFour;
        break;}
      default:
        break;
    }
    return value;
  }


}
