#KNOWN ISSUES - 

PRODUCTS NOT APPEARING IN THE SITE 

- The most likely issues are:

The Angular app may be using SSR (Server-Side Rendering) which fails to fetch from the API during server-side rendering
There could be a CSS issue where elements are rendered but not visible
The *ngIf="!loading" condition might not be resolving to false