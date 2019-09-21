from django.conf.urls import url

from . import views

urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'^planner$', views.planner, name='planner'),
    url(r'^answer$', views.answer, name='answer'),
    url(r'^budget$', views.budget, name='budget'),
    url(r'^index$', views.index, name='index'),
]