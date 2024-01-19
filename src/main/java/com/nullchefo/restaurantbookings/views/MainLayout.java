package com.nullchefo.restaurantbookings.views;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import org.vaadin.lineawesome.LineAwesomeIcon;

import com.nullchefo.restaurantbookings.configuration.security.AuthenticatedUser;
import com.nullchefo.restaurantbookings.entity.User;
import com.nullchefo.restaurantbookings.views.about.AboutView;
import com.nullchefo.restaurantbookings.views.admindashboard.AdminDashboardView;
import com.nullchefo.restaurantbookings.views.checkoutform.CheckoutFormView;
import com.nullchefo.restaurantbookings.views.creditcardform.CreditCardFormView;
import com.nullchefo.restaurantbookings.views.helloworld.HelloWorldView;
import com.nullchefo.restaurantbookings.views.mycustomview.MyCustomViewView;
import com.nullchefo.restaurantbookings.views.ordersdetail.OrdersDetailView;
import com.nullchefo.restaurantbookings.views.restaurantaddress.RestaurantAddressView;
import com.nullchefo.restaurantbookings.views.restaurantdashboard.RestaurantDashboardView;
import com.nullchefo.restaurantbookings.views.restaurants.RestaurantsView;
import com.nullchefo.restaurantbookings.views.restaurantview.RestaurantViewView;
import com.nullchefo.restaurantbookings.views.supportchat.SupportChatView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

	private H2 viewTitle;

	private AuthenticatedUser authenticatedUser;
	private AccessAnnotationChecker accessChecker;

	public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
		this.authenticatedUser = authenticatedUser;
		this.accessChecker = accessChecker;
		// setPrimarySection(Section.DRAWER);
		setPrimarySection(Section.DRAWER);
		addDrawerContent();
		addHeaderContent();
	}

	private void addHeaderContent() {
		DrawerToggle toggle = new DrawerToggle();
		toggle.setAriaLabel("Menu toggle");

		viewTitle = new H2();
		viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

		addToNavbar(true, toggle, viewTitle);
	}

	private void addDrawerContent() {
		H1 appName = new H1("Restaurant booking");
		appName.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);
		Header header = new Header(appName);

		Scroller scroller = new Scroller(createNavigation());

		addToDrawer(header, scroller, createFooter());
	}

	private SideNav createNavigation() {
		SideNav nav = new SideNav();

		if (accessChecker.hasAccess(HelloWorldView.class)) {
			nav.addItem(new SideNavItem("Hello World", HelloWorldView.class, LineAwesomeIcon.GLOBE_SOLID.create()));

		}
		if (accessChecker.hasAccess(AboutView.class)) {
			nav.addItem(new SideNavItem("About", AboutView.class, LineAwesomeIcon.FILE.create()));

		}
		if (accessChecker.hasAccess(RestaurantsView.class)) {
			nav.addItem(new SideNavItem("Restaurants", RestaurantsView.class, LineAwesomeIcon.FILTER_SOLID.create()));

		}
		if (accessChecker.hasAccess(RestaurantAddressView.class)) {
			nav.addItem(new SideNavItem("Restaurant Address", RestaurantAddressView.class,
										LineAwesomeIcon.MAP_MARKER_SOLID.create()));

		}
		if (accessChecker.hasAccess(CheckoutFormView.class)) {
			nav.addItem(new SideNavItem("Checkout Form", CheckoutFormView.class, LineAwesomeIcon.CREDIT_CARD.create()));

		}
		if (accessChecker.hasAccess(CreditCardFormView.class)) {
			nav.addItem(new SideNavItem("Credit Card Form", CreditCardFormView.class,
										LineAwesomeIcon.CREDIT_CARD.create()));

		}
		if (accessChecker.hasAccess(RestaurantViewView.class)) {
			nav.addItem(new SideNavItem("Restaurant View", RestaurantViewView.class,
										LineAwesomeIcon.TH_LIST_SOLID.create()));

		}
		if (accessChecker.hasAccess(AdminDashboardView.class)) {
			nav.addItem(new SideNavItem("Admin Dashboard", AdminDashboardView.class,
										LineAwesomeIcon.CHART_AREA_SOLID.create()));

		}
		if (accessChecker.hasAccess(RestaurantDashboardView.class)) {
			nav.addItem(new SideNavItem("Restaurant Dashboard", RestaurantDashboardView.class,
										LineAwesomeIcon.CHART_AREA_SOLID.create()));

		}
		if (accessChecker.hasAccess(OrdersDetailView.class)) {
			nav.addItem(
					new SideNavItem("Orders Detail", OrdersDetailView.class, LineAwesomeIcon.COLUMNS_SOLID.create()));

		}
		if (accessChecker.hasAccess(SupportChatView.class)) {
			nav.addItem(new SideNavItem("Support Chat", SupportChatView.class, LineAwesomeIcon.COMMENTS.create()));

		}
		if (accessChecker.hasAccess(MyCustomViewView.class)) {
			nav.addItem(new SideNavItem("My Custom View", MyCustomViewView.class,
										LineAwesomeIcon.PENCIL_RULER_SOLID.create()));

		}

		return nav;
	}

	private Footer createFooter() {
		Footer layout = new Footer();

		Optional<User> maybeUser = authenticatedUser.get();
		if (maybeUser.isPresent()) {
			User user = maybeUser.get();
			String userNames = user.getFirstName() + " " + user.getLastName();

			Avatar avatar = new Avatar(userNames);
			StreamResource resource = new StreamResource(
					"profile-pic",
					() -> new ByteArrayInputStream(user.getProfilePicture()));
			avatar.setImageResource(resource);
			avatar.setThemeName("xsmall");
			avatar.getElement().setAttribute("tabindex", "-1");

			MenuBar userMenu = new MenuBar();
			userMenu.setThemeName("tertiary-inline contrast");

			MenuItem userName = userMenu.addItem("");
			Div div = new Div();
			div.add(avatar);
			div.add(userNames);
			div.add(new Icon("lumo", "dropdown"));
			div.getElement().getStyle().set("display", "flex");
			div.getElement().getStyle().set("align-items", "center");
			div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
			userName.add(div);
			userName.getSubMenu().addItem("Sign out", e -> {
				authenticatedUser.logout();
			});

			layout.add(userMenu);
		} else {
			Anchor loginLink = new Anchor("login", "Sign in");
			layout.add(loginLink);
		}

		return layout;
	}

	@Override
	protected void afterNavigation() {
		super.afterNavigation();
		viewTitle.setText(getCurrentPageTitle());
	}

	private String getCurrentPageTitle() {
		PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
		return title == null ? "" : title.value();
	}
}