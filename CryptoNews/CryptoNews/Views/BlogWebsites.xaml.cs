using CryptoNews.Models;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CryptoNews.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class BlogWebsites : ContentPage
    {
        public BlogWebsites()
        {
            InitializeComponent();
            BlogWebsitesListView.ItemsSource = App.database.GetAllBlogWebsites();
        }

        private async void SingleBlogWebsiteSelected(object sender, SelectedItemChangedEventArgs e)
        {
       
                await Navigation.PushAsync(new EditBlogWebsite()
                {
                    BindingContext = e.SelectedItem as BlogWebsite
                });
            
        }
        protected override void OnAppearing()
        {
            base.OnAppearing();
            ((App)App.Current).ResumeAtBlogWebsiteId = -1;
            ObservableCollection<BlogWebsite> blogWebsites = new ObservableCollection<BlogWebsite>(App.database.GetAllBlogWebsites());
            BlogWebsitesListView.ItemsSource = blogWebsites;

        }

            private void ResetButtonClicked(object sender, EventArgs e)
        {
            App.database.ResetBlogWebsite();
            DisplayAlert("Successful", "Website Preferences restored back to default", "Ok");
            BlogWebsitesListView.ItemsSource = null;
            BlogWebsitesListView.ItemsSource = App.database.GetAllBlogWebsites();
        }
    }
}