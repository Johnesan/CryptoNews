using CryptoNews.Database;
using CryptoNews.Models;
using CryptoNews.Services;
using CryptoNews.Views;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Xamarin.Forms;

namespace CryptoNews
{
    public partial class App : Application
    {
          private static CryptoNewsDB _database;
        public static CryptoNewsDB database
        {
            get
            {
                if (_database == null)
                {
                    _database = new CryptoNewsDB(DependencyService.Get<IFileHelper>().GetLocalFilePath("CryptoNews.db3"));
                }
                return _database;
            }
        }

        public int ResumeAtBlogWebsiteId { get; set; }

        public App()
        {
            InitializeComponent();
            InitializeSeedData();

            MainPage = new Home();
        }

        public static async Task InitializeSeedData()
        {
            var SampleBlogWebsites = new List<BlogWebsite>();
            var Website1 = new BlogWebsite
            {
                Name = "CCN",
                Url = "https://ccn.com/"
            };
            var Website2 = new BlogWebsite
            {
                Name = "CryptoClarified",
                Url = "https://cryptoclarified.com"
            };           
           
            var Website3 = new BlogWebsite
            {
                Name = "Crypto Scoop",
                Url = "http://cryptoscoop.net"
            };
            var Website4 = new BlogWebsite
            {
                Name = "Crypto Recorder",
                Url = "https://cryptorecorder.com"
            };
            SampleBlogWebsites.Add(Website1);
            SampleBlogWebsites.Add(Website2);
            SampleBlogWebsites.Add(Website3);
            SampleBlogWebsites.Add(Website4);


            foreach (var sampleWebsite in SampleBlogWebsites)
            {
                var dbBlogWebsites = database.GetAllBlogWebsites();
                if (dbBlogWebsites.Count >= 4)
                {
                    return;
                }
                if(dbBlogWebsites.Find(x => x.Url == sampleWebsite.Url) == null)
                {
                    database.AddBlogWebsite(sampleWebsite);
                }
            }
            
        }


        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
