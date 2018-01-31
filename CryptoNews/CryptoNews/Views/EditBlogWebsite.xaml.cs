using CryptoNews.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;

using Xamarin.Forms;
using Xamarin.Forms.Xaml;

namespace CryptoNews.Views
{
    [XamlCompilation(XamlCompilationOptions.Compile)]
    public partial class EditBlogWebsite : ContentPage
    {
        public EditBlogWebsite()
        {
            InitializeComponent();
            BindingContext = new BlogWebsite();
            SaveButton.IsEnabled = false;
            TestActivityIndicator.IsEnabled = false;
            TestActivityIndicator.IsVisible = false;
            TestActivityIndicator.IsRunning = false;
        }
    
        private async void SaveButtonClicked(object sender, EventArgs e)
        {
            var blogWebsite = (BlogWebsite)BindingContext;
            App.database.UpdateBlogWebsite(blogWebsite);
            await Navigation.PopAsync();
            
        }

        private async void TestButtonClicked(object sender, EventArgs e)
        {
            var url = UrlEntry.Text;
            SaveButton.IsEnabled = false;
            TestActivityIndicator.IsEnabled = true;
            TestActivityIndicator.IsVisible = true;
            TestActivityIndicator.IsRunning = true;

            EntryFrame.IsEnabled = false;
            try
            {

                HttpClient httpClient = new HttpClient();
                httpClient.BaseAddress = new Uri(url);
                httpClient.DefaultRequestHeaders.Accept.Clear();
                httpClient.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
                var response = await httpClient.GetAsync("/wp-json/wp/v2/posts?_embed");
                if (response.IsSuccessStatusCode)
                {
                    await DisplayAlert("Successful", "This Website has been tested and it meets the requirements to fetch its blog feed. You can now save it", "Ok");
                    SaveButton.IsEnabled = true;                

                }
                else
                {
                    await DisplayAlert("Failed", "This Website does not meet the requirements so this application cannot fetch its feed.", "Ok");
                   
                }
            }
            catch(Exception ex)
            {
               await  DisplayAlert("Unhandled Exception", "There was a problem while trying to test this Url. We can't tell if it meets the requirements or not. Please try again later", "Ok");
                
            }
            TestActivityIndicator.IsEnabled = false;
            TestActivityIndicator.IsVisible = false;
            TestActivityIndicator.IsRunning = false;

            EntryFrame.IsEnabled = true;

        }

        private void UrlEntryBindingContextChanged(object sender, EventArgs e)
        {
            SaveButton.IsEnabled = false;
        }
    }
}