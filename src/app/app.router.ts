import { IndexRoutes } from './index/index.router';
import { HomeRoutes } from './home/home.router';
import { Route } from '@angular/router';
import { NoPageRoutes } from './no-page/nopage.route';

export const routers:Route[]=[...HomeRoutes,...IndexRoutes,...NoPageRoutes]